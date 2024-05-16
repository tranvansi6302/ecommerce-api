package com.tranvansi.ecommerce.services.colors;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.entities.Color;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.ColorMapper;
import com.tranvansi.ecommerce.repositories.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorService implements IColorService{
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public ColorResponse createColor(CreateColorRequest request) {
        if(colorRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.COLOR_NAME_ALREADY_EXISTS);
        }
        if(colorRepository.existsByHex(request.getHex())){
            throw new AppException(ErrorCode.COLOR_HEX_ALREADY_EXISTS);
        }
        Color color = colorMapper.toColor(request);
        return colorMapper.toColorResponse(colorRepository.save(color));
    }

    @Override
    public Page<ColorResponse> getAllColors(PageRequest pageRequest) {
        return colorRepository.findAll(pageRequest).map(colorMapper::toColorResponse);
    }

    @Override
    public ColorResponse getColorById(String id) {
        return colorMapper.toColorResponse(colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND)));
    }
}
