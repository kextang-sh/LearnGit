package com.stubhub.payment.seller.accounting.infra.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfig {
  @Bean
  @Throws(Exception::class)
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    http.csrf().disable()
    http.authorizeExchange()
      .pathMatchers("/health")
      .permitAll()
    http.authorizeExchange()
      .pathMatchers("/payments/**", "/nocheck/**","/vendorStatement/**")
      .authenticated()
    http.oauth2ResourceServer().jwt()
    return http.build()
  }
}
