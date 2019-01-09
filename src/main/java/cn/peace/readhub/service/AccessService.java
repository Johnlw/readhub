package cn.peace.readhub.service;

import cn.peace.readhub.domain.Visitor;

import java.util.List;

public interface AccessService {
    List<Visitor> listAccessRecordToday();
}
