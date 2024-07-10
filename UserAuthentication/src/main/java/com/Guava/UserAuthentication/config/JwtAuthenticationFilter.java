package com.Guava.UserAuthentication.config;

import com.Guava.UserAuthentication.token.JwtTokenService;
import com.Guava.UserAuthentication.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //GET AUTHENTICATION HEADER
        String authHeader = request.getHeader("Authorisation");
        //set jwt token
        String jwtToken = null;
        // set username
        String userEmail = null;

        // validate token headers
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            // kill the execution code
            return;
        }

        // set JWT token value retrieved from autherization header
        jwtToken = authHeader.substring(7);

        //extract the user email from JWT token
        userEmail = jwtTokenService.extractUsername(jwtToken);

        //Check if the user email is present and if the user is not already authenticated.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            //Validate token
            if (jwtTokenService.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken userAuthToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,null,
                                userDetails.getAuthorities()
                        );
                //SET USER AUTH TOKEN DETAILS OBJECT.
                userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //SET SECURITY CONTEXT HOLDER AUTHENTICATION.
                SecurityContextHolder.getContext().setAuthentication(userAuthToken);
            }

            //move to the next filter
            filterChain.doFilter(request,response);
        }

    }
}

/*
Summarized Flow:
Check if the user email is present and if the user is not already authenticated.
Load the user's details using a custom service.
Validate the JWT token.
Create an authentication token with the user's details and authorities.
Set additional details for the authentication token.
Set the authentication token in the security context.
*/

