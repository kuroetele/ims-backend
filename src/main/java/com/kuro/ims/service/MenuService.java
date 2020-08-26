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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService
{
    private final MenuRepository menuRepository;


    public List<Menu> getMenus(Role role)
    {
        List<Menu> menus = new ArrayList<>();
        List<Menu> all = menuRepository.findAll(Sort.by("priority"));
        if (role == Role.SALES_PERSON)
        {
            Menu others = new Menu();
            others.setVisible(false);
            others.setSubMenus( // menu is admin but submenu is user
                all
                    .stream()
                    .filter(m -> m.getRole() != Role.SALES_PERSON)
                    .flatMap(m -> m.getSubMenus().stream())
                    .filter(s -> s.getRole() == Role.SALES_PERSON)
                    .collect(Collectors.toList())
            );

            menus.add(others);

            all // menu is user and not submenu
                .stream()
                .filter(m -> m.getRole() != Role.ADMIN && Collections.isEmpty(m.getSubMenus()))
                .forEach(menus::add);

            all //menu is user but submenu might contain admin
                .stream()
                .filter(m -> m.getRole() != Role.ADMIN)
                .flatMap(m -> m.getSubMenus().stream())
                .filter(s -> s.getRole() == Role.SALES_PERSON)
                .collect(Collectors.groupingBy(SubMenu::getMenu))
                .forEach((menu, subMenus) -> menus.add(menu));

            return menus;
        }
        else
        {
            return all;
        }
    }
}
