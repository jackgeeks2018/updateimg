package com.wpp.service;


import com.wpp.dao.updateimgMapper;
import com.wpp.pojo.updateimg;
import com.wpp.pojo.updateimgExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadImgImp implements UploadImg {
    @Autowired
    private updateimgMapper dao;

    @Override
    public void upimg(updateimg img) {
        dao.insert(img);
    }

    @Override
    public updateimg doewimhg(String name) {
        updateimgExample Example = new updateimgExample();
        updateimgExample.Criteria Criteria = new updateimgExample().createCriteria();
        Criteria.andIdEqualTo(name);
        return dao.selectByExample(Example).get(0);
    }


    public List<updateimg> getlixt() {
        return dao.selectByExample(null);

    }

}
