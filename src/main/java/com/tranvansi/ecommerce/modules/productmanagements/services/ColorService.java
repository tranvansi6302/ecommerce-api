package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Color;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.ColorMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ColorRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateColorRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateColorRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IColorService;

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
        Color color = findColorById(id);
        return colorMapper.toColorResponse(color);
    }

    @Override
    public ColorResponse updateColor(Integer id, UpdateColorRequest request) {
        Color color = findColorById(id);
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
        Color color = findColorById(id);
        colorRepository.delete(color);
    }

    @Override
    public Color findColorById(Integer id) {
        return colorRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
    }
}
