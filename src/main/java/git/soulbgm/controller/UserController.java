package git.soulbgm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import git.soulbgm.common.pojo.PageInfo;
import git.soulbgm.common.pojo.ResultData;
import git.soulbgm.common.pojo.ReturnCode;
import git.soulbgm.mapper.UserMapper;
import git.soulbgm.pojo.User;
import git.soulbgm.utils.ModelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("findByPage")
    public ResultData findByPage(Integer pageNum, Integer pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.select(user);
        PageInfo pageInfo = PageInfo.getPageInfo((Page) list);
        return ResultData.getResultData(ReturnCode.SUCCESS, pageInfo);
    }

    @GetMapping("find")
    public ResultData find(User user) {
        return ResultData.getResultData(ReturnCode.SUCCESS, userMapper.select(user));
    }

    @PostMapping("save")
    public ResultData save(@RequestBody User user) {
        ModelBuilder.builder(user);
        int result = userMapper.upsert(user);
        return ResultData.getResultData(result > 0 ? ReturnCode.SUCCESS : ReturnCode.SAVE_FAIL);
    }

    @PostMapping("insertList")
    public ResultData save(@RequestBody List<User> userList) {
        for (User user : userList) {
            ModelBuilder.builder(user);
        }
        int result = userMapper.upsertList(userList);
        return ResultData.getResultData(result > 0 ? ReturnCode.SUCCESS : ReturnCode.SAVE_FAIL);
    }

    @PostMapping("del")
    public ResultData del(String ids) {
        int result = userMapper.deleteByIds(ids);
        return ResultData.getResultData(result > 0 ? ReturnCode.SUCCESS : ReturnCode.REMOVE_FAIL);
    }

    @PostMapping("update")
    public ResultData update(@RequestBody User user) {
        int result = userMapper.upsert(user);
        return ResultData.getResultData(result > 0 ? ReturnCode.SUCCESS : ReturnCode.REMOVE_FAIL);
    }

    private String formatIds(String ids) {
        String[] split = ids.split(",");
        StringBuilder idsStringBuilder = new StringBuilder();
        for (String s : split) {
            idsStringBuilder.append("'");
            idsStringBuilder.append(s);
            idsStringBuilder.append("',");
        }
        if (idsStringBuilder.length() > 0) {
            idsStringBuilder.deleteCharAt(idsStringBuilder.length() - 1);
        }
        return idsStringBuilder.toString();
    }

}
