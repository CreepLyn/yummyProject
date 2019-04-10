package com.yummy.businessLogic;

import com.yummy.businessLogicService.AddressBLService;
import com.yummy.modal.Address;
import com.yummy.repository.AddressRepository;
import com.yummy.repository.UserRepository;
import com.yummy.util.ConvertUtil;
import com.yummy.util.ResultMessage;
import com.yummy.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBL implements AddressBLService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    /**
     * 得到用户的地址列表
     * @param username 用户名
     * @return 地址列表
     */
    @Override
    public List<AddressVO> getAllByUsername(String username) {
        List<Address> addressList = addressRepository.getAllByUserid(userRepository.findByUsername(username).getId());
        List<AddressVO> addressVOList = new ArrayList<AddressVO>();
        for (Address addressItem: addressList){
            AddressVO addressVO = ConvertUtil.toAddressVO(addressItem);
            addressVO.setValid(true);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    /**
     * 增加地址
     * @param username 用户名
     * @param name 收货人
     * @param telephone 收货人联系方式
     * @param sex 收货人性别
     * @param description 详细地址
     * @return 操作结果
     */
    @Override
    public List<AddressVO> addAddress(String username, String name, String telephone, String sex, String description) {
        Address address = new Address();
        address.setName(name);
        address.setSex(sex);
        address.setTelephone(telephone);
        address.setDescription(description);
        address.setUserid(userRepository.findByUsername(username).getId());
        addressRepository.save(address);
        return getAllByUsername(username);
    }

    /**
     * 修改地址
     * @param id 地址ID
     * @param name 收货人
     * @param telephone 收货人联系方式
     * @param sex 收货人性别
     * @param description 详细地址
     * @return 操作结果
     */
    @Override
    public String modifyAddress(Long id, String name, String telephone, String sex, String description) {
        Address address = addressRepository.getAddressById(id);
        address.setName(name);
        address.setSex(sex);
        address.setTelephone(telephone);
        address.setDescription(description);
        addressRepository.save(address);
        return ResultMessage.SUCCESS;
    }

    /**
     * 删除地址
     * @param id 地址ID
     * @return 操作结果
     */
    @Override
    public String deleteAddress(Long id) {
        addressRepository.deleteById(id);
        return ResultMessage.SUCCESS;
    }
}
