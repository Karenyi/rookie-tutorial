package org.tutorial.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;
import org.tutorial.model.vo.DeptVO;
import org.tutorial.model.vo.EmpVO;
import org.tutorial.service.DeptService;
import org.tutorial.service.EmpService;

@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptService deptService;

    @GetMapping("/emp/listAll")
    public String listAll(Model model) {
        List<EmpDO> empDOs = empService.getAll();
        model.addAttribute("empVOs", transformEmpVOs(empDOs));
        return "emp/listAll";
    }

    @PostMapping("/emp/getOne_For_Display")
    public String listOne(Model model, Integer empno) {
        EmpDO empDO = empService.getOneEmp(empno);
        model.addAttribute("empVO", transformEmpVO(empDO));
        return "emp/listOne";
    }

    @GetMapping("/emp/add")
    public String showAddPage(Model model) {
        model.addAttribute("empVO", new EmpVO());
        model.addAttribute("deptVOs", transformDeptVOs(deptService.getAll()));
        return "emp/add";
    }

    @PostMapping("/emp/add")
    public String add(@ModelAttribute("empVO") EmpVO empVO) {
        empService.addEmp(
                empVO.getEname(),
                empVO.getJob(),
                parse(empVO.getHiredate()),
                empVO.getSal(),
                empVO.getComm(),
                empVO.getDeptno());
        return "redirect:/emp/listAll";
    }

    @PostMapping("/emp/getOne_For_Update")
    public String showUpdatePage(Model model, Integer empno) {
        EmpDO empDO = empService.getOneEmp(empno);
        List<DeptDO> deptDOs = deptService.getAll();
        model.addAttribute("deptVOs", transformDeptVOs(deptDOs));
        model.addAttribute("empVO", transformEmpVO(empDO));
        return "emp/update";
    }

    @PostMapping("/emp/update")
    public String update(Model model, EmpVO empVO) {
        EmpDO updatedEmpDO = empService.updateEmp(
                empVO.getEmpno(),
                empVO.getEname(),
                empVO.getJob(),
                parse(empVO.getHiredate()),
                empVO.getSal(),
                empVO.getComm(),
                empVO.getDeptVO().getDeptno());
        model.addAttribute("empVO", transformEmpVO(updatedEmpDO));
        return "emp/listOne";
    }

    @PostMapping("/emp/delete")
    public String delete(Integer empno) {
        empService.deleteEmp(empno);
        return "redirect:/emp/listAll";
    }

    private List<EmpVO> transformEmpVOs(List<EmpDO> empDOs) {
        return empDOs.stream()
                .map(this::transformEmpVO)
                .collect(Collectors.toList());
    }

    private List<DeptVO> transformDeptVOs(List<DeptDO> deptDOs) {
        return deptDOs.stream()
                .map(this::transformDeptVO)
                .collect(Collectors.toList());
    }

    private EmpVO transformEmpVO(EmpDO empDO) {
        EmpVO empVO = new EmpVO();
        empVO.setEmpno(empDO.getEmpno());
        empVO.setEname(empDO.getEname());
        empVO.setJob(empDO.getJob());
        empVO.setHiredate(format(empDO.getHiredate()));
        empVO.setSal(empDO.getSal());
        empVO.setComm(empDO.getComm());
        empVO.setDeptVO(transformDeptVO(empDO.getDeptDO()));
        return empVO;
    }

    private DeptVO transformDeptVO(DeptDO deptDO) {
        DeptVO deptVO = new DeptVO();
        deptVO.setDeptno(deptDO.getDeptno());
        deptVO.setDname(deptDO.getDname());
        deptVO.setLoc(deptDO.getLoc());
        return deptVO;
    }

    private String format(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(localDate);
    }

    private LocalDate parse(String LocalDataString) {
        return LocalDate.parse(LocalDataString);
    }

}