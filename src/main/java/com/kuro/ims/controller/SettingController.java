package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Setting;
import com.kuro.ims.service.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/settings")
public class SettingController
{

    private final SettingService settingService;


    @GetMapping("/default")
    public Response<Setting> getSetting()
    {
        return Response.<Setting>builder()
            .data(settingService.getStoreSetting())
            .build();
    }


    @PostMapping("/{id}")
    public void updateSetting(@PathVariable Long id, @RequestBody Setting setting)
    {
        settingService.updateSetting(id, setting);
    }
}
