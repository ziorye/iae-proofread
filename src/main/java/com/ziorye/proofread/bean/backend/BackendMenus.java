package com.ziorye.proofread.bean.backend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "backend")
@Data
public class BackendMenus {
    public List<Menu> menus;
}
