package com.kuro.ims.service;

import com.kuro.ims.entity.Menu;
import com.kuro.ims.entity.SubMenu;
import com.kuro.ims.repository.MenuRepository;
import com.kuro.ims.type.Role;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService
{
    private final MenuRepository menuRepository;


    public List<Menu> getMenus(Role role)
    {
        List<Menu> menus = new ArrayList<>();
        if (role == Role.USER)
        {
            menuRepository.findAll()
                .stream()
                .filter(m->m.getRole() != Role.ADMIN && Collections.isEmpty(m.getSubMenus()))
                .forEach(menus::add);

            menuRepository.findAll()
                .stream()
                .flatMap(m -> m.getSubMenus().stream())
                .filter(s -> s.getRole() == Role.USER)
                .collect(Collectors.groupingBy(SubMenu::getMenu))
                .forEach((menu, subMenus) -> menus.add(menu));

            return menus;
        }
        else
        {
            return menuRepository.findAll();
        }
    }
}
