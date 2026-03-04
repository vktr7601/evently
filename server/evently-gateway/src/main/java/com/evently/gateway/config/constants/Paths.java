package com.evently.gateway.config.constants;

public class Paths {
    public static final String[] PUBLIC_PATHS = {
            "/auth/login",
            "/user/register",
            "/events/**",
            "/locations/**",
            "/artists/**",
            "/categories/**",
    };

    public static final String[] AUTHENTICATED_PATHS = {
            "/orders/**",
            "/user/**",
            "/follows/**",
            "/tickets/**",
            "/bookings/**",
            "/promo-codes/**",
            "/refunds/**",
            "/payment-transactions/**",
            "/notifications/**"
    };

    public static final String[] ADMIN_PATHS = {
            "/admin/**"
    };
}