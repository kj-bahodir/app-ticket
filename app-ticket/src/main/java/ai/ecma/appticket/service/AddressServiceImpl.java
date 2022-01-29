package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Address;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.AddressResDto;
import ai.ecma.appticket.payload.AddressReqDto;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.repository.AddressRepository;
import ai.ecma.appticket.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<List<AddressResDto>> getAll() {
        List<AddressResDto> addressResDtoList =new ArrayList<>();
        for (Address address : addressRepository.findAll()) {
            AddressResDto addressResDto = CustomMapper.addressToDto(address);
            addressResDtoList.add(addressResDto);
        }
        return ApiResult.successResponse(addressResDtoList);
    }

    @Override
    public ApiResult<AddressResDto> getAddress(UUID id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bunday Address topilmadi"));
        return ApiResult.successResponse(CustomMapper.addressToDto(address));
    }

    @Override
    public ApiResult<AddressResDto> add(AddressReqDto addressDto) {
        Address address=new Address(
                addressDto.getLat(),
                addressDto.getLon(),
                addressDto.getName(),
                addressDto.getTarget(),
                attachmentRepository.findById(addressDto.getPhotoId())
                        .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"photo topilmadi"))
        );
        addressRepository.save(address);
        return ApiResult.successResponse(CustomMapper.addressToDto(address));
    }

    @Override
    public ApiResult<AddressResDto> edit(AddressReqDto addressDto, UUID id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Address topilmadi"));
        address.setLat(addressDto.getLat());
        address.setLon(addressDto.getLon());
        address.setName(addressDto.getName());
        address.setTarget(addressDto.getTarget());

        address.setPhoto(attachmentRepository.findById(addressDto.getPhotoId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"photo topilmadi")));
        addressRepository.save(address);
        return ApiResult.successResponse(CustomMapper.addressToDto(address));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try{
            addressRepository.deleteById(id);
            return ApiResult.successResponse("O'chirildi");
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException(HttpStatus.NOT_FOUND,"Topilmadi");
        }
    }
}
