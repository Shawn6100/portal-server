package cn.edu.gdufs.controller;

import cn.edu.gdufs.common.PageResult;
import cn.edu.gdufs.config.interceptor.RequiredPermission;
import cn.edu.gdufs.constant.RoleConstant;
import cn.edu.gdufs.controller.dto.CarouselInsertDTO;
import cn.edu.gdufs.controller.dto.CarouselUpdateDTO;
import cn.edu.gdufs.controller.vo.AdminDetailVO;
import cn.edu.gdufs.controller.vo.CarouselForAdminVO;
import cn.edu.gdufs.pojo.Admin;
import cn.edu.gdufs.pojo.Carousel;
import cn.edu.gdufs.service.AdminService;
import cn.edu.gdufs.service.CarouselService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Description:
 * Author: 严仕鹏
 * Date: 2022/12/3
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController extends BaseController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private AdminService adminService;

    /**
     * 查询轮播图列表
     */
    @GetMapping()
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public PageResult<CarouselForAdminVO> getCarouselList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                          @RequestParam(defaultValue = "5") Integer pageSize) {
        // 分页查询轮播图列表
        List<Carousel> carouselList = carouselService.getCarouselList(pageNumber, pageSize);
        PageResult<CarouselForAdminVO> result = new PageResult<>();
        BeanUtils.copyProperties(carouselList, result);

        // 将轮播图列表转换为轮播图VO列表
        result.setList(carouselService.getCarouselVOList(carouselList));

        return result;
    }

    /**
     * 查询轮播图详情信息
     */
    @GetMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public CarouselForAdminVO getCarouselDetail(@PathVariable long id) {
        // 查询轮播图详情
        Carousel carousel = carouselService.getCarouselDetail(id);

        // 数据模型转换
        CarouselForAdminVO carouselForAdminVO = new CarouselForAdminVO();
        BeanUtils.copyProperties(carousel, carouselForAdminVO);

        // 添加创建者信息和修改者信息
        carouselForAdminVO.setCreateUser(adminService.getAdminDetail(carousel.getCreateUserId()));
        carouselForAdminVO.setUpdateUser(adminService.getAdminDetail(carousel.getUpdateUserId()));

        return carouselForAdminVO;
    }

    /**
     * 新增轮播图
     */
    @PostMapping()
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public CarouselForAdminVO insertCarousel(@RequestBody @Valid CarouselInsertDTO carouselInsertDTO) {
        // 数据模型转换
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselInsertDTO, carousel);

        // 新增轮播图
        carouselService.insertCarousel(carousel, getUserId());

        // 数据模型转换
        CarouselForAdminVO carouselForAdminVO = new CarouselForAdminVO();
        BeanUtils.copyProperties(carousel, carouselForAdminVO);

        // 查询创建用户信息，并添加到 VO 中
        Admin admin = adminService.getAdminById(carousel.getCreateUserId());
        AdminDetailVO adminDetailVO = new AdminDetailVO(admin.getId(), admin.getUsername(),
                admin.getRole(), admin.getNickname(), admin.getEmail());
        carouselForAdminVO.setCreateUser(adminDetailVO);

        return carouselForAdminVO;
    }

    /**
     * 修改轮播图
     */
    @PutMapping()
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void updateCarousel(@RequestBody @Valid CarouselUpdateDTO carouselUpdateDTO) {
        // 数据模型转换
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselUpdateDTO, carousel);

        // 修改轮播图
        carouselService.updateCarousel(carousel, getUserId());
    }

    /**
     * 删除轮播图
     */
    @DeleteMapping("/{id}")
    @RequiredPermission({RoleConstant.ROLE_SUPER_ADMIN, RoleConstant.ROLE_NORMAL_ADMIN})
    public void deleteCarousel(@PathVariable long id) {
        carouselService.deleteCarousel(id);
    }

}
