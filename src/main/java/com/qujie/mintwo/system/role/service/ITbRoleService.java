package com.qujie.mintwo.system.role.service;

import com.baomidou.mybatisplus.service.IService;
import com.qujie.mintwo.system.role.entity.TbRole;
import com.qujie.mintwo.ustils.PageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */
public interface ITbRoleService extends IService<TbRole> {
    /**
     * 角色列表
     * @param params
     * @return
     */
    PageUtil roleList(Map<String, Object> params);
    /**
     * 根据id修改
     */
    boolean updateByIds(TbRole role);


    boolean saves(TbRole role);

    boolean batchImport(String fileName, MultipartFile file) throws Exception;




}
