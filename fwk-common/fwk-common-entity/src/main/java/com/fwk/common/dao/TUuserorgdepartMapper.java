package com.fwk.common.dao;

import com.fwk.common.entity.TUuserorgdepart;
import com.fwk.common.entity.TUuserorgdepartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUuserorgdepartMapper {
    int countByExample(TUuserorgdepartExample example);

    int deleteByExample(TUuserorgdepartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUuserorgdepart record);

    int insertSelective(TUuserorgdepart record);

    List<TUuserorgdepart> selectByExample(TUuserorgdepartExample example);

    TUuserorgdepart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUuserorgdepart record, @Param("example") TUuserorgdepartExample example);

    int updateByExample(@Param("record") TUuserorgdepart record, @Param("example") TUuserorgdepartExample example);

    int updateByPrimaryKeySelective(TUuserorgdepart record);

    int updateByPrimaryKey(TUuserorgdepart record);
}