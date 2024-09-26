package me.truec0der.mwhitelist.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.config.ConfigRegister;
import me.truec0der.mwhitelist.impl.repository.RepositoryRegister;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public abstract class Service {
    ServiceRegister serviceRegister;
    RepositoryRegister repositoryRegister;
    ConfigRegister configRegister;
}
