package com.example.umc3_teamproject.config.oauth;


import com.example.umc3_teamproject.domain.LoginType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, LoginType> {
    /**
     * Controller에서 전달 받는 공급자 정보의 대문자 값 맵핑
     * @param source @PathVariable
     * @return SocialLoginType
     */
    @Override
    public LoginType convert(String source) {
        return LoginType.valueOf(source.toUpperCase());
    }
}