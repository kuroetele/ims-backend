package com.kuro.ims.repository;

import com.kuro.ims.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long>
{
}
