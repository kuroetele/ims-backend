package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Menu;
import com.kuro.ims.service.MenuService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menus")
@RestController
@AllArgsConstructor
public class MenuController
{

    private final MenuService menuService;


    @GetMapping
    public Response<List<Menu>> getMenus()
    {
        return Response.<List<Menu>>builder()
            .data(menuService.getMenus())
            .build();
    }


}
