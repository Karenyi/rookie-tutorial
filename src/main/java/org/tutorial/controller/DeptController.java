package org.tutorial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;
import org.tutorial.model.vo.DeptVO;
import org.tutorial.service.DeptService;

@Controller
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/listAll")
    public String listAll(Model model) {
        List<DeptDO> deptDOs = deptService.getAll();
        List<DeptVO> deptVOS = transformVOs(deptDOs);
        model.addAttribute("deptVOS", deptVOS);
        return "dept/listAll";
    }

    @PostMapping("/dept/listEmps_ByDeptno_A")
    public String listEmpsByDeptnoA(Model model, Integer deptno) {
        ModelAndView modelAndView = new ModelAndView();
        List<EmpDO> empDOs = deptService.getEmpsByDeptno(deptno);
        model.addAttribute("listEmps_ByDeptno", empDOs);
        return "dept/listEmpsByDeptno";
    }

    @GetMapping("/dept/listEmps_ByDeptno_B/{deptno}")
    public ModelAndView listEmpsByDeptnoB(@PathVariable("deptno") Integer deptno) {
        ModelAndView modelAndView = new ModelAndView();
        List<DeptDO> deptDOs = deptService.getAll();
        List<EmpDO> empDOs = deptService.getEmpsByDeptno(deptno);
        modelAndView.addObject("deptVOS", transformVOs(deptDOs));
        modelAndView.addObject("listEmps_ByDeptno", empDOs);
        modelAndView.setViewName("dept/listAll");
        return modelAndView;
    }

    @PostMapping("/dept/getOne_For_Update_Dept")
    public String getOneForUpdate(Model model, Integer deptno) {
        DeptDO deptDO = deptService.getOneDept(deptno);
        DeptVO deptVO = transformVO(deptDO);
        model.addAttribute("deptVO", deptVO);
        return "dept/update";
    }

    @PostMapping("/dept/update")
    public String update(Model model, @ModelAttribute("deptVO") DeptVO deptVO) {
        DeptDO updatedDeptDO = deptService.update(deptVO.getDeptno(), deptVO.getDname(), deptVO.getLoc());
        model.addAttribute("deptVO", transformVO(updatedDeptDO));
        return "dept/listOne";
    }

    @PostMapping("/dept/delete_Dept")
    private String deleteDept(Integer deptno) {
        deptService.deleteDept(deptno);
        return "redirect:/dept/listAll";
    }

    private List<DeptVO> transformVOs(List<DeptDO> deptDOs) {
        return deptDOs
                .stream()
                .map(this::transformVO)
                .collect(Collectors.toList());
    }

    private DeptVO transformVO(DeptDO deptDO) {
        DeptVO deptVO = new DeptVO();
        deptVO.setDeptno(deptDO.getDeptno());
        deptVO.setDname(deptDO.getDname());
        deptVO.setLoc(deptDO.getLoc());
        return deptVO;
    }

}