package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user,folderNames);
        log.info("folerName {}",folderNames);
        log.info("existFolderList: {}", existFolderList);
        List<Folder> fodlerList = new ArrayList<>();

        for (String folderName : folderNames) {
            if(!isExistFoldername(folderName, existFolderList)){
                Folder folder = new Folder(folderName, user);
                fodlerList.add(folder);
            } else{
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }
        }

        folderRepository.saveAll(fodlerList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findAllByUser(user);
        ArrayList<FolderResponseDto> responseDtoList = new ArrayList<>();

        for (Folder folder : folderList) {
            responseDtoList.add(new FolderResponseDto(folder));
        }

        return responseDtoList;
    }

    private boolean isExistFoldername(String folderName, List<Folder> existFolderList) {
        for (Folder folder : existFolderList) {
            if(folder.getName().equals(folderName))
                return true;
        }
        return false;
    }

}
