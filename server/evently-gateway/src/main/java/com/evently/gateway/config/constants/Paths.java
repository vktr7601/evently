package com.evently.gateway.config.constants;

public class Paths {
    public static final String[] PUBLIC_PATHS = {
            "/auth/login",
            "/auth/refresh",
            "/user/register",
            "/events/**",
            "/locations/**",
            "/artists/**",
            "/categories/**"
    };

    public static final String[] AUTHENTICATED_PATHS = {
            "/user/**",
            "/follows/**",
            "/tickets/**",
            "/orders/**",
            "/refunds/**",
            "/promo-codes/validate",
            "/promo-codes/**",
            "/payment-transactions/**",
            "/notifications/**",
    };

    public static final String[] ADMIN_PATHS = {
            "/admin/users/**",
            "/admin/events/**",
            "/admin/locations/**",
            "/admin/artists/**",
            "/admin/categories/**",
            "/admin/orders/**",
            "/admin/promo-codes/**",
            "/admin/payment-transactions/**",
            "/admin/notifications/**",
    };
}