package cn.peace.readhub.mapper;

import cn.peace.readhub.domain.AccRcdDO;
import cn.peace.readhub.domain.Visitor;

import java.util.List;

public interface AccessMapper {
    void saveAccessRecord(List<AccRcdDO> accList);

    List<Visitor> listAccessRecordToday();
}
