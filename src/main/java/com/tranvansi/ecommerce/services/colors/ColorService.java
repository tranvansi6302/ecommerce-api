package com.tranvansi.ecommerce.services.colors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.requests.colors.UpdateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.entities.Color;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.ColorMapper;
import com.tranvansi.ecommerce.repositories.ColorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorService implements IColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public ColorResponse createColor(CreateColorRequest request) {
        if (colorRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.COLOR_NAME_ALREADY_EXISTS);
        }
        if (colorRepository.existsByHex(request.getHex())) {
            throw new AppException(ErrorCode.COLOR_HEX_ALREADY_EXISTS);
        }
        Color color = colorMapper.createColor(request);
        return colorMapper.toColorResponse(colorRepository.save(color));
    }

    @Override
    public Page<ColorResponse> getAllColors(PageRequest pageRequest) {
        return colorRepository.findAll(pageRequest).map(colorMapper::toColorResponse);
    }

    @Override
    public ColorResponse getColorById(Integer id) {
        return colorMapper.toColorResponse(
                colorRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND)));
    }

    @Override
    public ColorResponse updateColor(Integer id, UpdateColorRequest request) {
        Color color =
                colorRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
        if (colorRepository.existsByName(request.getName())
                && !color.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.COLOR_NAME_ALREADY_EXISTS);
        }
        if (colorRepository.existsByHex(request.getHex())
                && !color.getHex().equals(request.getHex())) {
            throw new AppException(ErrorCode.COLOR_HEX_ALREADY_EXISTS);
        }
        colorMapper.updateColor(color, request);
        return colorMapper.toColorResponse(colorRepository.save(color));
    }

    @Override
    public void deleteColor(Integer id) {
        Color color =
                colorRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
        colorRepository.delete(color);
    }
}
