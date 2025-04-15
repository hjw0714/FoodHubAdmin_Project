package com.application.foodhubAdmin.controller;

import com.application.foodhubAdmin.dto.request.BannerRequest;
import com.application.foodhubAdmin.dto.response.banner.BannerResponse;
import com.application.foodhubAdmin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<List<BannerResponse>> getAllBanners() {
        return ResponseEntity.ok(bannerService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBanner(@RequestPart("requestDto") BannerRequest requestDto , @RequestParam("imageFile")MultipartFile imageFile) throws IOException {
        bannerService.saveBanner(requestDto , imageFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateBanner(@PathVariable Long id , @RequestPart("requestDto") BannerRequest requestDto ,  @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        bannerService.updateBanner(id, requestDto, imageFile);
        return ResponseEntity.ok().build();
    }
}
