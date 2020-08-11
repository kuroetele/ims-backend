package com.kuro.ims.service;

import com.kuro.ims.entity.Menu;
import com.kuro.ims.repository.MenuRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService
{
    private final MenuRepository menuRepository;


    public List<Menu> getMenus()
    {
        return menuRepository.findAll();
    }
}
