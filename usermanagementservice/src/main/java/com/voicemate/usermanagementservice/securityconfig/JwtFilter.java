package com.voicemate.usermanagementservice.securityconfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.voicemate.usermanagementservice.service.JwtService;
import com.voicemate.usermanagementservice.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter { // OncePerRequestFilter-For request iw will executed once.

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException { // request - to with data on reques of user ; response -we can add
													// when response

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // Skipping Bearer .
			userName = jwtService.extractUserNameFromToken(token);
		}

		/*
		 * SecurityContextHolder.getContext().getAuthentication() - if it is null then
		 * it means user has not been authenticated already.
		 */
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);

			if (jwtService.validateToken(token, userDetails)) {

				/*
				 * After Validation next,the default first filter will be
				 * UsernamePasswordAuthentication as we know.So now we are going to pass it with
				 * token,incoming request.
				 */

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				// setting incoming request details with other filters and so on.
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// since token verified we are setting the token into the SecurityContext.
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}

		}

		/*
		 * After verifying token since we set it as primary checking above,security will
		 * be going to next filter.
		 */
		filterChain.doFilter(request, response);
	}

}
