package com.wpp.service;

import com.wpp.dao.QerallTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QerAllDtaeimpl implements QerallDate {
    @Autowired
    public QerallTest dao;
    @Override
    public String getdate() {
        return dao.QerallDate();
    }
}
