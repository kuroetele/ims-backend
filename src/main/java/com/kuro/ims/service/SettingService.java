package com.kuro.ims.service;

import com.kuro.ims.entity.Setting;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.exception.ImsException;
import com.kuro.ims.repository.SettingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SettingService
{
    private final SettingRepository settingRepository;


    public Setting getStoreSetting()
    {
        return settingRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new ImsException("could not find any store setting"));
    }


    public void updateSetting(Long id, Setting setting)
    {
        if (!settingRepository.existsById(id))
        {
            throw new ImsClientException("could not find setting");
        }
        setting.setId(id);
        settingRepository.save(setting);
    }
}
