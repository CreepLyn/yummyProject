package com.yummy.businessLogicService;

import com.yummy.vo.AddressVO;

import java.util.List;

public interface AddressBLService {

    List<AddressVO> getAllByUsername(String username);

    List<AddressVO> addAddress(String username, String name, String telephone, String sex, String description);

    String modifyAddress(Long id, String name, String telephone, String sex, String description);

    String deleteAddress(Long id);

}
