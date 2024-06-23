package com.example.services;

import com.example.entity.HealthData;
import com.example.entity.UserInfo;
import com.example.mapper.HealthDataMapper;
import com.example.mapper.UserInfoMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class HealthDataServices {
    @Resource
    private HealthDataMapper healthDataMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    public List<HealthData> getAll(String username) {
        return this.healthDataMapper.getAll(username);
    }

    public Boolean update(HealthData healthData) {
        if (healthData != null) {
            this.healthDataMapper.updateById(healthData);
            return true;
        }
        return false;
    }
    public Boolean delete(int id) {
        HealthData healthData = this.healthDataMapper.selectById(id);
        if (healthData != null) {
            this.healthDataMapper.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean importData(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        // 跳过第一行
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            HealthData healthData = new HealthData();
            healthData.setUserId((int) row.getCell(1).getNumericCellValue());
            healthData.setUsername(row.getCell(2).getStringCellValue());
            healthData.setBloodPressure(row.getCell(3).getStringCellValue());
            healthData.setBloodSugar(row.getCell(4).getStringCellValue());
            healthData.setCholesterol(row.getCell(5).getStringCellValue());
            healthData.setBmi(row.getCell(6).getStringCellValue());
            healthData.setVision(row.getCell(7).getStringCellValue());
            healthData.setHearing(row.getCell(8).getStringCellValue());
            healthData.setBoneMineralDensity(row.getCell(9).getStringCellValue());
            healthData.setHeartRate(row.getCell(10).getStringCellValue());
            healthData.setPulmonaryFunction(row.getCell(11).getStringCellValue());
            healthData.setStrength(row.getCell(12).getStringCellValue());
            healthData.setThyroid(row.getCell(13).getStringCellValue());

            healthData.setUreaNitrogen(row.getCell(14).getStringCellValue());
            healthData.setHemoglobin(row.getCell(15).getStringCellValue());
            healthData.setReactiveProtein(row.getCell(16).getStringCellValue());
            healthData.setLeukocyte(row.getCell(17).getStringCellValue());
            healthData.setLymphocyte(row.getCell(18).getStringCellValue());
            healthData.setElectroencephalogram(row.getCell(19).getStringCellValue());
            healthData.setIndex((int) row.getCell(20).getNumericCellValue());
            this.healthDataMapper.insert(healthData);
        }
        return true;
    }
//    获取每次测试男女人数
    public List<Map<String,Object>> getAllData() {
        List<Map<String,Object>> res = new ArrayList<>();
        int index = this.healthDataMapper.getLast().getIndex();
        int manNum = 0;
        int womanNum = 0;
        for (int i = 1; i <= index; i++) {
            List<HealthData> list = this.healthDataMapper.getAllByIndex(index);
            for (HealthData healthData: list) {
                UserInfo userInfo = this.userInfoMapper.selectById(healthData.getUserId());
                if (userInfo.getSex().equals("nan")) {
                    manNum++;
                } else {
                    womanNum++;
                }
            }
            Map<String,Object> map = new HashMap<>();
            int[] arr = new int[2];
            arr[0] = manNum;
            arr[1] = womanNum;
            map.put("index", index);
            map.put("manNum", manNum);
            map.put("womanNum", womanNum);
            res.add(map);
        }
        return res;
    }
}
