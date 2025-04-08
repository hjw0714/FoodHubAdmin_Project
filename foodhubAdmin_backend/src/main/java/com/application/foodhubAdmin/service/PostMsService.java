package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.dto.response.YearlyTotalPostCntResponse;
import com.application.foodhubAdmin.repository.PostMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMsService {

    private PostMsRepository postMsRepository;

    public List<YearlyTotalPostCntResponse> getYearlyTotalPostCnt() {
        System.out.println(postMsRepository.getYearlyTotalPostCnt());
        return postMsRepository.getYearlyTotalPostCnt();
    }

}
