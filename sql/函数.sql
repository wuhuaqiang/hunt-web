--------------------------------------------------------
--  文件已创建 - 星期三-八月-08-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Function FUN_BDZDQDLYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZDQDLYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站地区电流
  ret_result varchar2(100);
begin

select sum(dianliu) into ret_result from t_yx_jiagong_byq where tqtype='地区'  and cmtype='高'and jgnf=nf and
byqid in (select t_byqxx_id from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid=bdzid);
  return ret_result;
end fun_bdzdqDlyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZDQWGYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZDQWGYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站地区无功
  ret_result varchar2(100);
begin

select sum(wugong) into ret_result from t_yx_jiagong_byq where tqtype='地区'  and cmtype='高'and jgnf=nf and
byqid in (select t_byqxx_id from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid=bdzid);
  return ret_result;
end fun_bdzdqWgyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZDQZDFHYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZDQZDFHYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站地区最大负荷
  ret_result varchar2(100);
begin

select sum(yougong) into ret_result from t_yx_jiagong_byq where tqtype='地区'  and cmtype='高'and jgnf=nf and
byqid in (select t_byqxx_id from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid=bdzid);
  return ret_result;
end fun_bdzdqZdFhyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZJFDLCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZJFDLCOUNT" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站年积分电量
  ret_result varchar2(100);
begin
select round(sum(t_jfdl_zongzhi),2) into ret_result from t_yx_bdzjfdl where t_jfdl_bdzid=bdzid
and t_jfdl_type='月' and substr(t_jfdl_data,0,4)=nf;

  return ret_result;
end fun_bdzJfdlCount;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZZDFHFSSKYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZZDFHFSSKYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站地区最大负荷发生时刻
  ret_result varchar2(100);
begin
  select max(zdfhfssk) into ret_result from t_yx_jiagong_byq where tqtype='地区'  and cmtype='高'and jgnf=nf and 
byqid in (select t_byqxx_id from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid=bdzid);
  return ret_result;
end fun_bdzZdFhfsskyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZZSZDFHFSSKYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZZSZDFHFSSKYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站自身最大负荷发生时刻
  ret_result varchar2(100);
begin
select zdfhfssk into ret_result from t_yx_jiagong_bdz where byqid= bdzid and tqtype='自身'
and jgnf=nf and rownum=1 order by yougong desc;
  return ret_result;
end fun_bdzZsZdFhfsskyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BDZZSZDFHYXBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BDZZSZDFHYXBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站自身最大负荷
  ret_result varchar2(100);
begin
select yougong into ret_result from t_yx_jiagong_bdz where byqid= bdzid and tqtype='自身'
and jgnf=nf and rownum=1 order by yougong desc;
  return ret_result;
end fun_bdzZsZdFhyxbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_BYQXGCISHUBYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_BYQXGCISHUBYBYQID" (byqid varchar2) return varchar2 is
xgcs number;
begin
  select count(T_Byqls_id) into xgcs from T_SBSJ_BYQLS where T_BYQXX_ID = byqid;
  return(xgcs);
end fun_ByqxgcishubyByqid;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZDQYXBYID_FZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZDQYXBYID_FZL" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站地区运行负载率
rl number;
szgl number;
fzl varchar2(50);
str varchar2(50);
yg number;
wg number;
begin
  select fun_GetBdzRlByBdzId(bdzid) into rl from dual;
  select fun_bdzdqZdFhyxbyid(bdzid,nf) into yg from dual;
  select fun_bdzdqWgyxbyid(bdzid,nf) into wg from dual;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  if(szgl=0)
  then
    fzl:=0;
    else
    select round((szgl/rl*100),2) into fzl from dual;
    end if;

    select substr(fzl,0,1) into str from dual;
    if(str='.')
    then
      fzl:='0'||fzl;
    end if;
  return fzl;
end fun_get_bdzdqyxbyid_fzl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZDQYXBYID_GLYS
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZDQYXBYID_GLYS" (bdzid in varchar2,nf in varchar2) return varchar2 is
--变电站地区功率因数
yg number;
szgl number;
glys varchar2(10);
str varchar2(10);
begin
  select fun_bdzdqZdFhyxbyid(bdzid,nf) into yg from dual;
  szgl:=fun_get_bdzdqyxbyid_szgl(bdzid,nf);
  if(szgl=0)
  then
    glys:=0;
  else
    select round(yg/szgl*100,2) into glys from dual;
    end if;
    select substr(glys,0,1) into str from dual;
    if(str = '.')
    then
      glys:='0'||glys;
      end if;

  return(glys);
end fun_get_bdzdqyxbyid_glys;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZDQYXBYID_SZGL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZDQYXBYID_SZGL" (bdzid in varchar2,nf in varchar2) return varchar2 is
--变电站地区视在功率
yg number;
wg number;
szgl varchar2(10);
str varchar2(10);
begin
  select fun_bdzdqZdFhyxbyid(bdzid,nf) into yg from dual;
  select fun_bdzdqWgyxbyid(bdzid,nf) into wg from dual;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  select substr(szgl,0,1) into str from dual;
    if(str='.')
    then
      szgl:='0'||szgl;
    end if;
  return(szgl);
end fun_get_bdzdqyxbyid_szgl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZSFHBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZSFHBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
--变电站省/年负荷
str varchar2(10);
begin
  select yougong into str from t_yx_jiagong_bdz where byqid=bdzid and jgnf=nf and rownum=1;
  return(str);
end fun_get_bdzSfhbyId;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZSFHZDFSSKBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZSFHZDFSSKBYID" (bdzid in varchar2,nf in varchar2) return varchar2 is
--变电站省/年最大负荷发生时刻
str varchar2(100);
begin
  select zdfhfssk into str from t_yx_jiagong_bdz where byqid=bdzid and jgnf=nf and rownum=1;
  return(str);
end fun_get_bdzSfhZdfsskbyId;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZYXBYID_FZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZYXBYID_FZL" (bdzid in varchar2,nf in varchar2,tq in varchar2) return varchar2 is
 --变电站自身运行负载率
 jgids varchar2(36);
  ret_result varchar2(100);
begin
  select jgid into jgids from t_yx_jiagong_bdz where byqid= bdzid and tqtype=tq and jgnf=nf and rownum=1 order by yougong desc;
  select fun_get_fzl_bybdzid(jgids) into ret_result from  dual;
  return ret_result;
end fun_get_bdzyxbyid_fzl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BDZZSYXBYID_FZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BDZZSYXBYID_FZL" (bdzid in varchar2,nf in varchar2) return varchar2 is
 --变电站自身运行负载率
 jgids varchar2(36);
  ret_result varchar2(100);
begin
  select jgid into jgids from t_yx_jiagong_bdz where byqid= bdzid and tqtype='自身' and jgnf=nf and rownum=1 order by yougong desc;
  select fun_get_fzl_bybdzid(jgids) into ret_result from  dual;
  return ret_result;
end fun_get_bdzZsyxbyid_fzl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_BYQYXBYID_FZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_BYQYXBYID_FZL" (byq in varchar2,nf in varchar2,tq in varchar2) return varchar2 is
 --变压器运行负载率
 jgids varchar2(36);
  ret_result varchar2(100);
begin
  select jgid into jgids from t_yx_jiagong_byq where byqid= byq and tqtype=tq and jgnf=nf and t_yx_jiagong_byq.cmtype='高' and rownum<=1;
  select fun_get_fzl_bybyqid(jgids) into ret_result from  dual;
  return ret_result;
end fun_get_byqyxbyid_fzl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_FZL_BYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_FZL_BYBDZID" (Id in varchar2) return varchar2 is
----变电站负载率
bdzid varchar2(36);
rl number;
szgl number;
fzl varchar2(50);
str varchar2(50);
begin
  select byqId into bdzid from T_Yx_JiaGong_Bdz where jgId=Id;
  select fun_GetBdzRlByBdzId(bdzid) into rl from dual;
  select FUN_GET_SZGL_BYBDZID(Id) into szgl from dual;
  if(szgl=0)
  then
    fzl:=0;
    else
    select round((szgl/rl*100),2) into fzl from dual;
    end if;

    select substr(fzl,0,1) into str from dual;
    if(str='.')
    then
      fzl:='0'||fzl;
    end if;
  return(fzl);
end FUN_GET_FZL_BYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_FZL_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_FZL_BYBYQID" (Id in varchar2) return varchar2 is
----变压器负载率
byqid varchar2(36);
rl number;
szgl number;
fzl varchar2(100);
str varchar2(100);
begin
  select byqid into byqid from T_Yx_JiaGong_Byq where jgId=Id;
  select t_Byqxx_Bdrl into rl from t_sbsj_byqxx where t_byqxx_id = byqid;
  select FUN_GET_SZGL_BYBYQID(Id) into szgl from dual;
  if(szgl=0)
  then
    fzl:=0;
    else
    select round((szgl/rl*100),2) into fzl from dual;
    end if;

    select substr(fzl,0,1) into str from dual;
    if(str='.')
    then
      fzl:='0'||fzl;
    end if;
  return(fzl);
end FUN_GET_FZL_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_FZL_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_FZL_BYGYXLID" (Id in varchar2) return varchar2 is
----高压负载率
gyxlId varchar2(50);
dl number;
aqdl number;
fzl varchar2(10);
str varchar2(10);
begin
  select XLID into gyxlId from t_yx_jiagong_jiaoliuxl where jgId=Id;
  select DIANLIU into dl from t_yx_jiagong_jiaoliuxl where jgId=Id;
  select T_GYXLXX_ZDYXDL into aqdl from T_Sbsj_GYXLXX where  T_gyxlxx_ID = gyxlId;
  if(aqdl=0)
  then
    fzl:=0;
      else
    select round((100 * dl /aqdl),2) into fzl from dual;
    end if;

    select substr(fzl,0,1) into str from dual;
    if(str='.')
    then
      fzl:='0'||fzl;
    end if;
  return(fzl);
end FUN_GET_FZL_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_FZL_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_FZL_BYZYXLID" (Id in varchar2) return varchar2 is
----中压负载率
zyxlId varchar2(50);
dl number;
aqdl number;
fzl varchar2(10);
str varchar2(10);
begin
  select XLID into zyxlId from T_Yx_JiaGong_ZhongYaXL where jgId=Id;
  select DIANLIU into dl from T_Yx_JiaGong_ZhongYaXL where jgId=Id;
  select T_ZYLLXXX_AQDL into aqdl from T_SBSJ_ZYLLXXX where  T_ZYLLXXX_ID = zyxlId;
  if(aqdl=0)
  then
    fzl:=0;
      else
    select round((100 * dl /aqdl),2) into fzl from dual;
    end if;

    select substr(fzl,0,1) into str from dual;
    if(str='.')
    then
      fzl:='0'||fzl;
    end if;
  return(fzl);
end FUN_GET_FZL_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GLYS_BYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GLYS_BYBDZID" (Id in varchar) return varchar2 is
--变电站功率因数
yg number;
szgl number;
glys varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_BDZ where jgId=Id;
  szgl:=FUN_GET_SZGL_BYBDZID(Id);
  if(szgl=0)
  then
    glys:=0;
  else
    select round(yg/szgl*100,2) into glys from dual;
    end if;
    select substr(glys,0,1) into str from dual;
    if(str = '.')
    then
      glys:='0'||glys;
      end if;

  return(glys);
end FUN_GET_GLYS_BYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GLYS_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GLYS_BYBYQID" (Id in varchar) return varchar2 is
--变压器功率因数
yg number;
szgl number;
glys varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_Byq where jgId=Id;
  szgl:=FUN_GET_SZGL_BYBYQID(Id);
  if(szgl=0)
  then
    glys:=0;
  else
    select round(yg/szgl*100,2) into glys from dual;
    end if;
    select substr(glys,0,1) into str from dual;
    if(str = '.')
    then
      glys:='0'||glys;
      end if;

  return(glys);
end FUN_GET_GLYS_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GLYS_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GLYS_BYGYXLID" (Id in varchar) return varchar2 is
--高压功率因数
yg number;
szgl number;
glys varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_JiaoLiuXL where jgId=Id;
  szgl:=FUN_GET_SZGL_BYGYXLID(Id);
  if(szgl=0)
  then
    glys:=0;
  else
    select round(yg/szgl*100,2) into glys from dual;
    end if;
    select substr(glys,0,1) into str from dual;
    if(str = '.')
    then
      glys:='0'||glys;
      end if;

  return(glys);
end FUN_GET_GLYS_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GLYS_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GLYS_BYZYXLID" (Id in varchar) return varchar2 is
--中压功率因数
yg number;
szgl number;
glys varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_ZhongYaXL where jgId=Id;
  szgl:=FUN_GET_SZGL_BYZYXLID(Id);
  if(szgl=0)
  then
    glys:=0;
  else
    select round(yg/szgl*100,2) into glys from dual;
    end if;
    select substr(glys,0,1) into str from dual;
    if(str = '.')
    then
      glys:='0'||to_char(glys);
      end if;

  return(glys);
end FUN_GET_GLYS_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GYDQANDSZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GYDQANDSZDFH" (gyxlId in varchar2,selYear in varchar2,lx in varchar2) return varchar2 is
  --高压线路地区/省最大负荷
  ret_result varchar2(100);
  str varchar2(10);
begin
   select max(yougong) into ret_result from t_yx_jiagong_jiaoliuxl where JGNF=selYear and xlId=gyxlId and tqtype =lx;
  select substr(ret_result,0,1) into str from dual;
    if(str = '.')
    then
      ret_result:='0'||to_char(ret_result);
      end if;
  return ret_result;
end fun_get_gydqandSzdfh;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_GYDQANDSZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_GYDQANDSZDFHFSSK" (gyxlId in varchar2,selYear in varchar2,lx in varchar2) return varchar2 is
  --高压线路地区/省最大负荷发生时刻
  ret_result varchar2(100);
  str varchar2(10);
begin
   select zdfhfssk into ret_result from t_yx_jiagong_jiaoliuxl where JGNF=selYear and xlId=gyxlId and tqtype =lx;

  return ret_result;
end fun_get_gydqandSzdfhfssk;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_SZGL_BYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_SZGL_BYBDZID" (Id in varchar2) return varchar2 is
--变电站视在功率
yg number;
wg number;
szgl varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_Bdz where jgId=Id;
  select wugong into wg from T_Yx_JiaGong_Bdz where jgId=Id;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  select substr(szgl,0,1) into str from dual;
    if(str='.')
    then
      szgl:='0'||szgl;
    end if;
  return(szgl);
end FUN_GET_SZGL_BYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_SZGL_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_SZGL_BYBYQID" (Id in varchar2) return varchar2 is
--变压器视在功率
yg number;
wg number;
szgl varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_Byq where jgId=Id;
  select wugong into wg from T_Yx_JiaGong_Byq where jgId=Id;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  select substr(szgl,0,1) into str from dual;
    if(str='.')
    then
      szgl:='0'||szgl;
    end if;
  return(szgl);
end FUN_GET_SZGL_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_SZGL_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_SZGL_BYGYXLID" (Id in varchar2) return varchar2 is
--高压视在功率
yg number;
wg number;
szgl varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_JiaoLiuXL where jgId=Id;
  select wugong into wg from T_Yx_JiaGong_JiaoLiuXL where jgId=Id;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  select substr(szgl,0,1) into str from dual;
    if(str='.')
    then
      szgl:='0'||szgl;
    end if;return(szgl);
end FUN_GET_SZGL_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_SZGL_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_SZGL_BYZYXLID" (Id in varchar2) return varchar2 is
--中压线路视在功率
yg number;
wg number;
szgl varchar2(10);
str varchar2(10);
begin
  select yougong into yg from T_Yx_JiaGong_ZhongYaXL where jgId=Id;
  select wugong into wg from T_Yx_JiaGong_ZhongYaXL where jgId=Id;
  select round(sqrt((yg*yg)+(wg*wg)),2) into szgl from dual;
  select substr(szgl,0,1) into str from dual;
  if(str='.')
  then
    szgl:='0'||szgl;
    end if;
  return(szgl);
end FUN_GET_SZGL_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_CTPX
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_CTPX" (xlid in varchar2) return varchar2 is
--判断CT变比偏小问题
  ct number;
  ret_result varchar2(36);
begin
  select substr(t_zyllxxx_ctyceddl,0,INSTR(t_zyllxxx_ctyceddl,'/',1,1)-1) into ct from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(ct<=300)
  then
    ret_result:='是';
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_ctpx;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_CWFJY
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_CWFJY" (xlid in varchar2) return varchar2 is
--判断城网非绝缘线问题
  cw varchar2(100);
  dxxh varchar2(100);
  typed varchar2(100);
  ret_result varchar2(36);
begin
  select fun_getcnwnamebyid(t_zyllxxx_ynw) into cw from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(cw='城网')
  then
    select t_zyllxxx_dxxh1 into dxxh from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
    select t_dxcs_xhtype into typed from t_Jcsj_Cs_Dxcs where t_dxcs_dxid=dxxh;

    if(typed<>'架空绝缘线')
    then
      ret_result:='是';
      else
        ret_result:='-';
        end if;
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_cwfjy;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_JXMS
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_JXMS" (xlid in varchar2) return varchar2 is
--判断接线模式问题
  llx varchar2(100);
  ret_result varchar2(36);
begin
  select fun_getlianluofangshibyxlid(t_zyllxxx_id) into llx from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(llx='无')
  then
    ret_result:='单辐射';
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_jxms;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_PBRL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_PBRL" (xlid in varchar2) return varchar2 is
--判断CT变比偏小问题
  zrl number;
  ret_result varchar2(36);
begin
  select t_zyllxxx_zrl into zrl from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(zrl<5 or zrl>10)
  then
    ret_result:=zrl||'';
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_pbrl;
--------


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_ZGXCD
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_ZGXCD" (xlid in varchar2) return varchar2 is
--判断主干线长度问题
  zgxcd number;
  ret_result varchar2(36);
begin
  select t_zyllxxx_zczgx into zgxcd from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(zgxcd>5)
  then
    ret_result:=zgxcd||'';
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_zgxcd;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WT_ZXJM
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WT_ZXJM" (xlid in varchar2) return varchar2 is
--判断最小截面问题
  dxmc varchar2(100);
  zxjm number;
  ret_result varchar2(36);
begin
  select T_ZYLLXXX_ZXJMDXZLL into dxmc from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(dxmc<>' ')
  then
    select t_dxcs_dxjm into zxjm from t_Jcsj_Cs_Dxcs where t_dxcs_xhmc=dxmc;

    if(zxjm<=240)
    then
      ret_result:=zxjm||'';
      else
        ret_result:='-';
        end if;
    else
      ret_result:='-';
    end if;

  return(ret_result);
end fun_get_wt_zxjm;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_CTPX
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_CTPX" (xlid in varchar2) return varchar2 is
--判断CT变比偏小问题级别
  ct number;
  ret_result varchar2(36);
begin
  select substr(t_zyllxxx_ctyceddl,0,INSTR(t_zyllxxx_ctyceddl,'/',1,1)-1) into ct from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(ct<=300)
  then
    ret_result:='是';
    else
      ret_result:='-';
    end if;
    if(ret_result='是')
    then
      ret_result:='';
      end if;

  return(ret_result);
end fun_get_wtjb_ctpx;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_CWFJY
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_CWFJY" (xlid in varchar2) return varchar2 is
--判断城网非绝缘线问题
  cw varchar2(100);
  dxxh varchar2(100);
  typed varchar2(100);
  ret_result varchar2(36);
begin
  select fun_getcnwnamebyid(t_zyllxxx_ynw) into cw from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(cw='城网')
  then
    select t_zyllxxx_dxxh1 into dxxh from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
    select t_dxcs_xhtype into typed from t_Jcsj_Cs_Dxcs where t_dxcs_dxid=dxxh;

    if(typed<>'架空绝缘线')
    then
      ret_result:='是';
      else
        ret_result:='-';
        end if;
    else
      ret_result:='-';
    end if;
    if(ret_result<>'-')
    then
      ret_result:='';
      end if;
  return(ret_result);
end fun_get_wtjb_cwfjy;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_JXMS
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_JXMS" (xlid in varchar2) return varchar2 is
--判断接线模式问题级别
  llx varchar2(100);
  ret_result varchar2(36);
begin
  select fun_getlianluofangshibyxlid(t_zyllxxx_id) into llx from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(llx='无')
  then
    ret_result:='单辐射';
    else
      ret_result:='-';
    end if;
    if(ret_result<>'-')
    then
      ret_result:='';
      end if;

  return(ret_result);
end fun_get_wtjb_jxms;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_PBRL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_PBRL" (xlid in varchar2) return varchar2 is
--判断配变容量问题级别
  zrl number;
  ret_result varchar2(36);
begin
  select t_zyllxxx_zrl into zrl from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(zrl<5 or zrl>10)
  then
    ret_result:=zrl||'';
    else
      ret_result:='-';
    end if;
    if(ret_result<>'-')
    then
      ret_result:='';
      end if;

  return(ret_result);
end fun_get_wtjb_pbrl;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_ZGXCD
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_ZGXCD" (xlid in varchar2) return varchar2 is
--判断主干线长度问题级别
  zgxcd number;
  ret_result varchar2(36);
begin
  select t_zyllxxx_zczgx into zgxcd from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(zgxcd>5)
  then
    ret_result:=zgxcd||'';
    else
      ret_result:='-';
    end if;
    if(ret_result<>'-')
    then
      ret_result:='';
      end if;
  return(ret_result);
end fun_get_wtjb_zgxcd;


/
--------------------------------------------------------
--  DDL for Function FUN_GET_WTJB_ZXJM
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET_WTJB_ZXJM" (xlid in varchar2) return varchar2 is
--判断最小截面问题级别
  dxmc varchar2(100);
  zxjm number;
  ret_result varchar2(36);
begin
  select T_ZYLLXXX_ZXJMDXZLL into dxmc from t_sbsj_zyllxxx where t_zyllxxx_id = xlid;
  if(dxmc<>' ')
  then
    select t_dxcs_dxjm into zxjm from t_Jcsj_Cs_Dxcs where t_dxcs_xhmc=dxmc;

    if(zxjm<=240)
    then
      ret_result:=zxjm||'';
      else
        ret_result:='-';
        end if;
    else
      ret_result:='-';
    end if;
    if(ret_result<>'-')
    then
      ret_result:='';
      end if;

  return(ret_result);
end fun_get_wtjb_zxjm;


/
--------------------------------------------------------
--  DDL for Function FUN_GET10KVCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET10KVCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='10';
  return(zs);
end FUN_GET10kVCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET10KVYYCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET10KVYYCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_Syzt='已使用' and T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='10';
  return(zs);
end FUN_GET10kVYYCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET110KVCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET110KVCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='110';
  return(zs);
end FUN_GET110kVCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET110KVYYCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET110KVYYCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_Syzt='已使用' and T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='110';
  return(zs);
end FUN_GET110kVYYCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET220KVCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET220KVCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='220';
  return(zs);
end FUN_GET220kVCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET220KVYYCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET220KVYYCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_Syzt='已使用' and T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='220';
  return(zs);
end FUN_GET220kVYYCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET35KVCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET35KVCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='35';
  return(zs);
end FUN_GET35KVCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GET35KVYYCXJGZSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GET35KVYYCXJGZSBYID" (bdzId in varchar2) return varchar2 is
zs varchar2(100);
begin
  select count(*) into zs from t_zygl_eelgs where T_EELGS_Syzt='已使用' and T_EELGS_BDZID = bdzId and fun_getdynamebyid(T_EELGS_DYDJ)='35';
  return(zs);
end FUN_GET35KVYYCXJGZSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETAZXSNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETAZXSNAMEBYID" (ids in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select t_azxs_name into ret_result from t_jcsj_azxs where t_azxs_id = ids;
  return(ret_result);
end fun_getazxsnamebyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZ_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZ_BYZYXLID" (xlId in varchar2) return varchar2 is
bdz varchar2(100);
begin
  select T_BDZXX_NAME into bdz from T_SBSJ_BDZXX where T_BDZXX_ID = (select T_ZYLLXXX_SSBDZ from T_SBSJ_ZYLLXXX where T_ZYLLXXX_ID = xlId);
  return(bdz);
end FUN_GETBDZ_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZ_ZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZ_ZDFHFSSK" (bdzId in varchar2,selYear in varchar2) return varchar2 is
  --变电站最大负荷发生时刻
  ret_result varchar2(100);
begin
   if(selYear='')
   then
     select ZDFHFSSK  into ret_result from (select sum(s.yougong) zdfh,
     Min(s.ZDFHFSSK) ZDFHFSSK , jgNf from T_YX_JIAGONG_BDZ s where
 byqId =bdzId group by jgnf order by Zdfh desc) where rownum=1;
   else
  select ZDFHFSSK into ret_result from (select sum(s.yougong) zdfh,Min(s.ZDFHFSSK) ZDFHFSSK, jgnf from T_YX_JIAGONG_BDZ s
   where byqID =bdzId and substr(jgnf,0,4) = SelYear
group by jgnf order by Zdfh desc) where rownum=1 ;
  end if;
  return ret_result;
end FUN_GETBDZ_ZDFHFSSK;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZBYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZBYBYQID" (byqId in varchar2) return varchar2 is
bdz varchar2(100);
begin

  select t_bdzxx_name into bdz from t_sbsj_bdzxx where t_bdzxx_id = (select t_byqxx_bdzid from t_sbsj_byqxx where t_byqxx_id=byqId);
  return(bdz);
end fun_getbdzbybyqid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZBYID" (bdzId in varchar2) return varchar2 is
bdz varchar2(100);
begin
  select t_bdzxx_name into bdz from t_sbsj_bdzxx where t_bdzxx_id = bdzId;
  return(bdz);
end Fun_getBDZbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZBYQTSBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZBYQTSBYBDZID" (bdzId in varchar2) return number is
byqts number;
begin
  select count(T_BYQXX_ID) into byqts from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId;
  return(byqts);
end fun_GetBdzBYQTSByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZCOUNT" (dydj in varchar2,nf in varchar2) return varchar2 is
--电压等级对应变电站个数
re_region varchar2(50);
begin
  select count(*) into re_region from t_sbsj_bdzxx where to_number(substr(t_bdzxx_btysj,0,4)) <= nf and t_bdzxx_dydj=
  (select t_dycs_id from t_jcsj_cs_dycs where t_dycs_name=dydj);
  return re_region;

end fun_GetBdzCount;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZRL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZRL" (dydj in varchar2,nf in varchar2) return varchar2 is
--电压等级对应变电站容量
re_region varchar2(50);
begin

select sum(t_sbsj_byqxx.t_byqxx_bdrl) into re_region from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid in(
select t_bdzxx_id from t_sbsj_bdzxx where to_number(substr(t_bdzxx_btysj,0,4)) <= nf and  t_bdzxx_dydj=
(select t_dycs_id from t_jcsj_cs_dycs where t_dycs_name=dydj));
  return re_region;

end fun_GetBdzRl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZRLBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZRLBYBDZID" (bdzId in varchar2) return number is
byqrl number;
begin
  select sum(T_BYQXX_BDRL) into byqrl from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId;
  return(byqrl);

end fun_GetBdzRlByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZRLBYBDZIDANDSJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZRLBYBDZIDANDSJ" (bdzId in varchar2,sj in varchar2) return number is
byqrl number;
begin
  select sum(T_BYQXX_BDRL) into byqrl from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId and to_date(T_BYQXX_BTYSJ,'yyyy-mm-dd') < to_date(sj,'yyyy-mm-dd') and ( T_byqxx_etysj is null or  to_date(T_byqxx_etysj,'yyyy-mm-dd') > to_date(sj,'yyyy-mm-dd') );
  return(byqrl);

end fun_GetBdzRlByBdzIdAndSJ;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZRLGOUCHENGBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZRLGOUCHENGBYBDZID" (bdzId in varchar2) return varchar2 is
rlgoucheng varchar2(200);

begin
 rlgoucheng :=' ';
declare cursor cs_byqInfo is select count(*) shuliang,T_BYQXX_BDRL from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId group by T_BYQXX_BDRL ;           
        cs_byqOne cs_byqInfo%rowtype;
       begin
       open cs_byqInfo;
       fetch cs_byqInfo into cs_byqOne;
         while cs_byqInfo%found loop
           IF cs_byqOne.shuliang = 1 THEN
               rlgoucheng := rlgoucheng || cs_byqOne.T_BYQXX_BDRL || '+';
       else 
               rlgoucheng := rlgoucheng || cs_byqOne.shuliang || '*' || cs_byqOne.T_BYQXX_BDRL || '+'; 
      END IF;
           fetch cs_byqInfo into cs_byqOne;
         end loop;
       close cs_byqInfo;
      end;
rlgoucheng := TRIM('+' from rlgoucheng);
  return(rlgoucheng);

end fun_GetBdzRlGouChengByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZTYPE
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZTYPE" (Ids in varchar2) return varchar2 is
--变电站类型
xlmc varchar2(50);
cishu number;
begin
  select count(T_BDZLSXX_ID) into cishu from t_Sbsj_Bdzlsxx where T_BDZXX_ID = ids;
  if(cishu=0)
  then
    return '新建';
  else
    return '改造';
  end if;

end fun_GetBdzType;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZWGBCGOUCHENGBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZWGBCGOUCHENGBYBDZID" (bdzId in varchar2) return varchar2 is
--无功补偿构成
wgbcgoucheng varchar2(200);
begin
 wgbcgoucheng :=' ';
declare cursor cs_byqInfo is select count(*) shuliang,t_byqxx_wgbcgc from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId group by t_byqxx_wgbcgc ;
        cs_byqOne cs_byqInfo%rowtype;
       begin
       open cs_byqInfo;
       fetch cs_byqInfo into cs_byqOne;
         while cs_byqInfo%found loop
           IF cs_byqOne.shuliang = 1 THEN
               wgbcgoucheng := wgbcgoucheng || cs_byqOne.t_byqxx_wgbcgc || '+';
       else
               wgbcgoucheng := wgbcgoucheng || cs_byqOne.shuliang || '*' || cs_byqOne.t_byqxx_wgbcgc || '+';
      END IF;
           fetch cs_byqInfo into cs_byqOne;
         end loop;
       close cs_byqInfo;
      end;
wgbcgoucheng := TRIM('+' from wgbcgoucheng);
  return(wgbcgoucheng);
end fun_GetBdzWgbcGouChengByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZWGBCRLBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZWGBCRLBYBDZID" (bdzId in varchar2) return number is
--无功补偿容量
byqrl number;
begin
  select sum(t_byqxx_wgbcrl) into byqrl from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId;
  return(byqrl);

end fun_GetBdzWgbcRlByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZXGCISHUBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZXGCISHUBYBDZID" (bdzId in varchar2) return number is
xgcs number;
begin
  select count(T_BDZLSXX_ID) into xgcs from t_Sbsj_Bdzlsxx where T_BDZXX_ID = bdzId;
  return(xgcs);
end fun_GetBdzXgcishuBYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZZRL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZZRL" (nf in varchar2) return varchar2 is
--变电站容量
re_region varchar2(50);
begin

select sum(t_sbsj_byqxx.t_byqxx_bdrl) into re_region from t_sbsj_byqxx where t_sbsj_byqxx.t_byqxx_bdzid in(
select t_bdzxx_id from t_sbsj_bdzxx where to_number(substr(t_bdzxx_btysj,0,4)) <= nf);
  return re_region;

end fun_GetBdzZRl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBDZZS
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBDZZS" (nf in varchar2) return varchar2 is
--变电站个数
re_region varchar2(50);
begin
  select count(*) into re_region from t_sbsj_bdzxx where to_number(substr(t_bdzxx_btysj,0,4)) <= nf ;
  return re_region;

end fun_GetBdzZs;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBYQ_ZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBYQ_ZDFHFSSK" (bdzId in varchar2,selYear in varchar2) return varchar2 is
  --变压器最大负荷发生时刻
  ret_result varchar2(100);
begin
   if(selYear='')
   then
     select ZDFHFSSK  into ret_result from (select sum(s.yougong) zdfh,
     Min(s.ZDFHFSSK) ZDFHFSSK , jgNf from T_YX_JIAGONG_BYQ s where
 byqId =bdzId group by jgnf order by Zdfh desc) where rownum=1;
   else
  select ZDFHFSSK into ret_result from (select sum(s.yougong) zdfh,Min(s.ZDFHFSSK) ZDFHFSSK, jgnf from T_YX_JIAGONG_BYQ s
   where byqID =bdzId and substr(jgnf,0,4) = SelYear
group by jgnf order by Zdfh desc) where rownum=1 ;
  end if;
  return ret_result;
end FUN_GETBYQ_ZDFHFSSK;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBYQTAISHURLBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBYQTAISHURLBYBDZID" (bdzId in varchar2) return number is
byqts number;
begin
  select count(T_BYQXX_ID) into byqts from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId;
  return(byqts);

end fun_GetBYQTAISHURlByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBYQTAISHURLBYBDZIDANDSJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBYQTAISHURLBYBDZIDANDSJ" (bdzId in varchar2,sj in varchar2) return number is
byqts number;
begin
  select count(T_BYQXX_ID) into byqts from T_sbsj_byqxx where T_BYQXX_BDZID = bdzId  and to_date(T_BYQXX_BTYSJ,'yyyy-mm-dd') < to_date(sj,'yyyy-mm-dd') and ( T_byqxx_etysj is null or  to_date(T_byqxx_etysj,'yyyy-mm-dd') > to_date(sj,'yyyy-mm-dd') );
  return(byqts);

end fun_GetBYQTAISHURlByBdzIdAndSJ;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBYQYX_ZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBYQYX_ZDFH" (byq in varchar2,nf in varchar2,tq in varchar2) return varchar2 is
 --变压器运行最大负荷
  ret_result varchar2(100);
begin
  select yougong into ret_result from t_yx_jiagong_byq where byqid= byq and tqtype=tq and jgnf=nf and t_yx_jiagong_byq.cmtype='高' and rownum<=1;
  return ret_result;
end fun_getbyqyx_Zdfh;


/
--------------------------------------------------------
--  DDL for Function FUN_GETBYQYX_ZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETBYQYX_ZDFHFSSK" (byq in varchar2,nf in varchar2,tq in varchar2) return varchar2 is
 --变压器运行最大负荷发生时刻
  ret_result varchar2(100);
begin
  select zdfhfssk into ret_result from t_yx_jiagong_byq where byqid= byq and tqtype=tq and jgnf=nf and t_yx_jiagong_byq.cmtype='高' and rownum<=1;
  return ret_result;
end fun_getbyqyx_Zdfhfssk;


/
--------------------------------------------------------
--  DDL for Function FUN_GETCNWNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETCNWNAMEBYID" (Id in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select t_cnw_name into ret_result from t_jcsj_cnw where t_cnw_id = Id;
  return(ret_result);
end FUN_GETCNWNAMEBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETCTBBNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETCTBBNAMEBYID" (Ids in varchar2) return varchar2 is
--CT变比
names varchar2(100);
begin
  select t_ctbb_Name into names from t_jcsj_ctbb where t_ctbb_id = Ids;
  return(names);
end Fun_getCtbbNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDIANLIUBYXLIDANDSJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDIANLIUBYXLIDANDSJ" (c_xlId in varchar2, shijian in varchar2) return number is
--获取电流
  qitaFuHe number;
begin
    select dianliu into qitaFuHe from t_yx_jiagong_ZYXLN where xlid=c_xlId and Jgnf=shijian;
  return(qitaFuHe);
end fun_GetDianliuByxlIdAndSJ;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDXXHBYDXID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDXXHBYDXID" (dxid in varchar2) return varchar2 is
dxxh varchar2(100);
begin
  select T_DXCS_XHMC into dxxh from T_JCSJ_CS_DXCS where T_DXCS_DXID = dxid;
  return(dxxh);
end fun_GetDxxhByDXID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDYDJ_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDYDJ_BYBYQID" (byqId in varchar2) return varchar2 is
dydj varchar2(100);
begin
  select T_DYCS_NAME into dydj from T_JCSJ_CS_DYCS where T_DYCS_ID = (select T_BDZXX_DYDJ from T_SBSJ_BDZXX where T_BDZXX_ID=(select T_BYQXX_BDZID from T_SBSJ_BYQXX where T_BYQXX_ID = byqId));
  return(dydj);
end FUN_GETDYDJ_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDYDJ_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDYDJ_BYGYXLID" (xlId in varchar2) return varchar2 is
dydj varchar2(100);
begin
  select T_DYCS_NAME into dydj from T_JCSJ_CS_DYCS where T_DYCS_ID = (select T_GYXLXX_DYDJ from T_SBSJ_GYXLXX where T_GYXLXX_ID = xlId);
  return(dydj);
end FUN_GETDYDJ_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDYDJBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDYDJBYBDZID" (bdzId in varchar2) return varchar2 is
dydj number;
begin
  select T_DYCS_NAME into dydj from T_JCSJ_CS_DYCS where T_DYCS_ID = (select T_Bdzxx_Dydj from T_SbSj_Bdzxx where T_Bdzxx_ID = bdzId);
  return(dydj);
end FUN_GETDYDJBYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDYDJBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDYDJBYID" (dydjid in varchar2) return varchar2 is
dydjname varchar2(36);
begin
  select t_dycs_name into dydjname from t_jcsj_cs_dycs where t_dycs_id=dydjid;
  return(dydjname);
end fun_getdydjbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETDYNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETDYNAMEBYID" (dy_Id in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select T_DYCS_NAME into ret_result from t_Jcsj_Cs_Dycs where T_DYCS_ID = dy_Id;
  return(ret_result);
end Fun_GetDyNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDFQBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDFQBYID" (city_id in varchar2) return varchar2 is
  ret_result varchar2(100);
begin
  select t_gdfq_name into ret_result from t_jcsj_fq_gdfq where  t_gdfq_id= city_id ;
  return ret_result;
end Fun_getgdfqbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDKKL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDKKL" (cnw in varchar2 ,nf in varchar2) return varchar2 is
--供电可靠率(RS-3)城农网
re_region varchar2(50);
begin

select round(sum(T_ZYPWYXZB_GDKKXRS3)/count(*))  into re_region from T_JCSJ_ZYPWYXZB where T_ZYPWYXZB_CNW=(select t_cnw_id from t_jcsj_cnw where t_cnw_name=cnw)
and  to_number(substr(T_ZYPWYXZB_YEAR,0,4)) <= nf;
 return re_region;

end fun_GetGdkkl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDQSXBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDQSXBYBDZID" (bdzId in varchar2) return varchar2 is
Gdqsx varchar2(100);
begin
  select t_bdzxx_gdqsx into Gdqsx from t_sbsj_bdzxx where t_bdzxx_id = bdzId;
  return(Gdqsx);
end fun_getGdqsxByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDQYNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDQYNAMEBYID" (qy_Id in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select T_GDFQ_NAME into ret_result from t_Jcsj_Fq_Gdfq where T_GDFQ_ID = qy_Id;
  return(ret_result);
end Fun_GetGdqyNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDXZBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDXZBYBDZID" (bdzId in varchar2) return varchar2 is
gdxz varchar2(100);
begin
  select t_bdzxx_gdxz into gdxz from t_sbsj_bdzxx where t_bdzxx_id = bdzId;
  return(gdxz);
end fun_getGdxzByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGDXZBYDWID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGDXZBYDWID" (ywdwId in varchar2) return varchar2 is
YWDW varchar2(36);
begin
  select t_sys_sx into YWDW from t_sys_ywdw where t_sys_id=ywdwId;
  return(YWDW);
end fun_getgdxzbydwid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGHKXXZTZ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGHKXXZTZ" (ids in varchar2) return varchar2 is
--规划库每一个节点总投资信息
ztz varchar2(100);
begin
  select sum(t_xmqc_xmtz) into ztz from t_Dwghgl_Xmqc where t_xmqc_id in (select t_ghkxx_xmqcid from t_dwghgl_ghkxx  where t_ghkxx_pid = ids);
  return (ztz);
end fun_getghkxxZtz;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQLX_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQLX_BYBYQID" (byqId in varchar2) return varchar2 is
GQLX varchar2(100);
begin
  select T_GQLX_NAME into GQLX from T_JCSJ_FQ_GQLX where T_GQLX_ID = (select T_BDZXX_GQLXID from T_SBSJ_BDZXX where T_BDZXX_ID=(select T_BYQXX_BDZID from T_SBSJ_BYQXX where T_BYQXX_ID = byqId));
  return(GQLX);
end FUN_GETGQLX_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQLX_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQLX_BYGYXLID" (xlId in varchar2) return varchar2 is
GQLX varchar2(100);
begin
  select T_GQLX_NAME into GQLX from T_JCSJ_FQ_GQLX where T_GQLX_ID = (select T_GYXLXX_GQLX from T_SBSJ_GYXLXX where T_GYXLXX_ID = xlId);
  return(GQLX);
end FUN_GETGQLX_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQLX_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQLX_BYZYXLID" (xlId in varchar2) return varchar2 is
GQLX varchar2(100);
begin
  select T_GQLX_NAME into GQLX from T_JCSJ_FQ_GQLX where T_GQLX_ID = (select T_ZYLLXXX_GQLX from T_SBSJ_ZYLLXXX where T_ZYLLXXX_ID = xlId);
  return(GQLX);
end FUN_GETGQLX_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQLXBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQLXBYBDZID" (bdzId in varchar2) return varchar2 is
gqlx varchar2(100);
begin
  select T_GQLX_NAME into gqlx from T_JCSJ_FQ_GQLX where T_GQLX_ID = (select T_BDZXX_GQLXID from T_SbSj_Bdzxx where T_Bdzxx_ID = bdzId);
  return(gqlx);
end FUN_GETGQLXBYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQLXNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQLXNAMEBYID" (gqlx_Id in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select T_GQLX_NAME into ret_result from t_Jcsj_Fq_Gqlx where T_GQLX_ID = gqlx_Id;
  return(ret_result);
end Fun_GetGqlxNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGQMJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGQMJ" (nf in varchar2) return varchar2 is
--供区面积
re_region varchar2(50);
begin
select sum(T_DQGKXX_TDMJ)  into re_region from T_JCSJ_DQGKXX where T_DQGKXX_YEAR <=nf  ;
 return re_region;

end fun_GetGqMj;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHBDZRLBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHBDZRLBYID" (gyghId in varchar2) return number is
bdrl number;
begin
  select SUM(T_GYBDZ_BDRL) into bdrl from T_DWGHGL_GYBDZ where t_dwghgl_id=gyghId;
  return(bdrl);
end FUN_GETGYGHBDZRLBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHBDZTZBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHBDZTZBYID" (gyghId in varchar2) return number is
bdztz number;
begin
  select SUM(T_GYBDZ_XMTZ) into bdztz from T_DWGHGL_GYBDZ where t_dwghgl_id=gyghId;
  return(bdztz);
end FUN_GETGYGHBDZTZBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHDLCDBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHDLCDBYID" (gyghId in varchar2) return number is
dlcd number;
begin
  select SUM(T_GYXL_DLCD) into dlcd from T_DWGHGL_GYXL where t_dwghgl_id=gyghId;
  return(dlcd);
end FUN_GETGYGHDLCDBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHJKCDBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHJKCDBYID" (gyghId in varchar2) return number is
jkcd number;
begin
  select SUM(T_GYXL_JKCD) into jkcd from T_DWGHGL_GYXL where t_dwghgl_id=gyghId;
  return(jkcd);
end FUN_GETGYGHJKCDBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHXLCDBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHXLCDBYID" (gyghId in varchar2) return number is
xlcd number;
begin
  select SUM(T_GYXL_ZCD) into xlcd from T_DWGHGL_GYXL where t_dwghgl_id=gyghId;
  return(xlcd);
end FUN_GETGYGHXLCDBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHXLTSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHXLTSBYID" (gyghId in varchar2) return number is
xlts number;
begin
  select SUM(T_GYXL_ZTS) into xlts from T_DWGHGL_GYXL where t_dwghgl_id=gyghId;
  return(xlts);
end FUN_GETGYGHXLTSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYGHXLTZBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYGHXLTZBYID" (gyghId in varchar2) return number is
xltz number;
begin
  select SUM(T_GYXL_XMTZ) into xltz from T_DWGHGL_GYXL where t_dwghgl_id=gyghId;
  return(xltz);
end FUN_GETGYGHXLTZBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYXL_ZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYXL_ZDFHFSSK" (gyxlId in varchar2,selYear in varchar2) return varchar2 is
  --高压线路最大负荷发生时刻
  ret_result varchar2(100);
begin
   if(selYear='')
   then
     select ZDFHFSSK  into ret_result from (select sum(s.yougong) zdfh,
     Min(s.ZDFHFSSK) ZDFHFSSK , jgNf from T_YX_JIAGONG_JIAOLIUXL s where
 xlId =gyxlId group by jgnf order by Zdfh desc) where rownum=1;
   else
  select ZDFHFSSK into ret_result from (select sum(s.yougong) zdfh,Min(s.ZDFHFSSK) ZDFHFSSK, jgnf from T_YX_JIAGONG_JIAOLIUXL s
   where xlId =gyxlId and substr(jgnf,0,4) = SelYear
group by jgnf order by Zdfh desc) where rownum=1 ;
  end if;
  return ret_result;
end FUN_GETGYXL_ZDFHFSSK;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYXLDLCDBYSJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYXLDLCDBYSJ" (xlId in varchar2,shijian in varchar2) return varchar2 is
jkxlcd number;
tiaoCount number;
begin
  select count(*) into tiaoCount from t_sbsj_gyxlxx_ls where T_Gyxlxx_Id = xlId  and
  to_date(T_GYXLXX_XGSJ,'yyyy-mm-dd') > to_date(shijian,'yyyy-mm-dd');
  if(tiaoCount>0)
  then
   select  to_number(nvl(t_gyxlxx_dl_qx,'0')) into jkxlcd from ( select t_gyxlxx_dl_qx  from t_sbsj_gyxlxx_ls  where T_Gyxlxx_Id = xlId and to_date(T_GYXLXX_XGSJ,'yyyy-mm-dd') > to_date(shijian,'yyyy-mm-dd') order by T_GYXLXX_XGSJ  asc)
     where rownum =1;
 else
     select to_number(nvl(t_gyxlxx_dl_qx,'0')) into jkxlcd  from t_sbsj_gyxlxx where T_GYXLXX_ID = xlId;
   end if;

  return(jkxlcd);
end FUN_GETGYXLDLCDBYSJ;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYXLJKCDBYSJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYXLJKCDBYSJ" (xlId in varchar2,shijian in varchar2) return varchar2 is
jkxlcd number;
tiaoCount number;
begin
  select count(*) into tiaoCount from t_sbsj_gyxlxx_ls where T_Gyxlxx_Id = xlId  and
  to_date(T_GYXLXX_XGSJ,'yyyy-mm-dd') > to_date(shijian,'yyyy-mm-dd');
  if(tiaoCount>0)
  then

   select t_gyxlxx_jkdx_qx into jkxlcd from (
   select t_gyxlxx_jkdx_qx  from t_sbsj_gyxlxx_ls  where T_Gyxlxx_Id = xlId and to_date(T_GYXLXX_XGSJ,'yyyy-mm-dd') > to_date(shijian,'yyyy-mm-dd') order by T_GYXLXX_XGSJ  asc)
     where rownum =1;

 else

     select t_gyxlxx_jkdx_qx into jkxlcd  from t_sbsj_gyxlxx where T_GYXLXX_ID = xlId;
   end if;

  return(jkxlcd);
end FUN_GETGYXLJKCDBYSJ;


/
--------------------------------------------------------
--  DDL for Function FUN_GETGYXLXGCISHUBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETGYXLXGCISHUBYID" (gyxlid in varchar2) return number is
 xgcs number;
begin
  select count(T_GYXLXX_LSID) into xgcs from t_sbsj_gyxlxx_ls where T_GYXLXX_ID = gyxlid;
  return(xgcs);
end Fun_GetGyxlxgCISHUbyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETISNUMBER
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETISNUMBER" (str in varchar2) return varchar2 is
--判断是否是数字
strs varchar2(10);
reges varchar2(10);
begin
  select str into strs  from dual  where regexp_like(str,'(^[+-]?\d{0,}\.?\d{0,}$)');
  if(strs<>null)
  then
    reges:='是';
    else
      reges:='否';
      end if;
  return(reges);
end fun_getIsNumber;


/
--------------------------------------------------------
--  DDL for Function FUN_GETJCMBNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETJCMBNAMEBYID" (JcMb_Id in varchar2) return varchar2 is
  ret_result varchar2(100);
begin
  select T_JcMb_Name into ret_result from t_Jcsj_Jcmb where T_Jcmb_Id = JcMb_Id;
  return(ret_result);
end fun_GetJcMbNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETJGSYZT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETJGSYZT" (openJgName varchar2) return varchar2 is
--判断使用状态
syzt varchar2(200);
begin
    if(instr(openJgName,'备用')>0 or instr(openJgName,'待用')>0)
    then
      syzt:='未使用';
      else
        syzt:='已使用';
        end if;

  return(syzt);
end fun_getJgSyzt;


/
--------------------------------------------------------
--  DDL for Function FUN_GETJSBYQNBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETJSBYQNBYBDZID" (bdzId in varchar2, shijian in varchar2) return varchar2 is
--根据变电站和时间判断是否通过N-1校验
  linshiXh varchar2(50);
  linshiTqType boolean;
begin
    declare cursor myCur is
  select jg from t_Jisuan_ByqN where jsnf=shijian and byqid in(select t_byqxx_id from t_sbsj_byqxx
   where t_byqxx_bdzid=bdzId);

 begin
       for aaa in myCur loop
           linshiXh := aaa.jg;
           if(linshiXh ='能')
           then
             linshiTqType:=true;
               else
             linshiTqType:=false;
               end if;
               end loop;
   end;
if(linshiTqType=true)
then
  linshiXh:='能';
  else
    linshiXh:='否';
    end if;
  return(linshiXh);
end fun_getJsByqNByBdzId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETJXSSGL_BYDXXHID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETJXSSGL_BYDXXHID" (dxid in varchar2,dydjname in varchar2) return varchar2 is
--极限输送功率
req_requper number;
aqdl number;
begin
  select t_dxcs_aqdlxz into aqdl from t_jcsj_cs_dxcs where t_dxcs_dxid=dxid;
  req_requper:=round(sqrt(3)*aqdl*to_number(dydjName)*0.95/1000);

  return(req_requper);
end fun_getJxssgl_byDxxhid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETLIANLUOFANGSHIBYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLIANLUOFANGSHIBYXLID" (xlId in varchar2) return varchar2 is
llfs varchar2(100);
lxCount int;
begin

    /*单联络、两联络、三联络、四联络、多联络*/
    select count(*) into lxCount from(
     select T_zyxllx_bid from t_Sbsj_Zyxllx where T_ZYXLLX_EID = xlId union select T_ZYXLLX_EID from t_Sbsj_Zyxllx where T_ZYXLLX_Bid = xlId);
     if (lxCount=0)
        then
          llfs :='单辐射';
        elsif (lxCount=1)
        then
          llfs :='单联络';
        elsif (lxCount=2)
        then
          llfs :='两联络';
        elsif (lxCount=3)
        then
          llfs :='三联络';
        elsif (lxCount=4)
        then
          llfs :='四联络';
        else
          llfs :='多联络';
          end if;


    return(llfs);
end fun_GetLianLuoFangShiByXlId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETLIANLUOFANGSHILSBYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLIANLUOFANGSHILSBYXLID" (xlId in varchar2,UpdataTime in varchar2) return varchar2 is
llfs varchar2(100);
lxCount int;
begin

    /*单联络、两联络、三联络、四联络、多联络*/
     select count(*) into lxCount from T_SbSj_LlxLs where T_LLXLS_BID = xlId and T_LLXLS_Updata = UpdataTime ;
     if (lxCount=0)
        then
          llfs :='无';
        elsif (lxCount=1)
        then
          llfs :='单联络';
        elsif (lxCount=2)
        then
          llfs :='两联络';
        elsif (lxCount=3)
        then
          llfs :='三联络';
        elsif (lxCount=4)
        then
          llfs :='四联络';
        else
          llfs :='多联络';
          end if;


    return(llfs);
end fun_GetLianLuoFangShiLsByXlId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETLIANLUOLSXIANNAME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLIANLUOLSXIANNAME" (xlId in varchar2,xlIndex number,leixing varchar2,cishu number) return varchar2 is
lxName varchar2(100);
begin
  if(leixing ='zj')
  then
             select T_ZYLLXXX_XLMC into lxName from ( select T_ZYLLXXX_XLMC,rownum rowIndex from t_sbsj_zyllxxx  where T_ZYLLXXX_SSBDZ <> (select T_ZYLLXXX_SSBDZ from t_sbsj_zyllxxx where T_ZYLLXXX_ID = xlId)
             and T_ZYLLXXX_ID in(
             select T_LLXLS_BID from T_SbSj_LlxLs where T_LLXLS_EID = xlId and T_LLXLS_CISHU=cishu union
             select T_LLXLS_EID from T_SbSj_LlxLs where T_LLXLS_BID = xlId and T_LLXLS_CISHU=cishu
             ) order by T_ZYLLXXX_XLMC) where rowIndex = xlIndex;
    else
            select T_ZYLLXXX_XLMC into lxName from ( select T_ZYLLXXX_XLMC,rownum rowIndex from t_sbsj_zyllxxx  where T_ZYLLXXX_SSBDZ = (select T_ZYLLXXX_SSBDZ from t_sbsj_zyllxxx where T_ZYLLXXX_ID = xlId)
             and T_ZYLLXXX_ID in(
             select T_LLXLS_BID from T_SbSj_LlxLs where T_LLXLS_EID = xlId and T_LLXLS_CISHU=cishu union
             select T_LLXLS_EID from T_SbSj_LlxLs where T_LLXLS_BID = xlId and T_LLXLS_CISHU=cishu
             ) order by T_ZYLLXXX_XLMC) where rowIndex = xlIndex;
      end if;
    return(lxName);
end fun_GetLianLuoLsxianName;



/
--------------------------------------------------------
--  DDL for Function FUN_GETLIANLUOXIANJXSL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLIANLUOXIANJXSL" (zyxlId in Varchar2) return number is
  xg_count number ;
begin
  --select count(T_ZyxLlx_Id) into xg_count from T_SbSj_ZyxLlx where T_ZyxLlx_BId = zyxlId or T_ZyxLlx_EId=zyxlId;
  select count(*) into xg_count from(
     select T_zyxllx_bid from t_Sbsj_Zyxllx where T_ZYXLLX_EID = zyxlId union select T_ZYXLLX_EID from t_Sbsj_Zyxllx where T_ZYXLLX_Bid = zyxlId);

  return(xg_count);
end Fun_GetLianLuoXianJxsl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETLIANLUOXIANNAME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLIANLUOXIANNAME" (xlId in varchar2,xlIndex number,leixing varchar2) return varchar2 is
lxName varchar2(100);
begin
  if(leixing = 1)
  then
             select T_ZYLLXXX_XLMC into lxName from ( select T_ZYLLXXX_XLMC,rownum rowIndex from t_sbsj_zyllxxx  where T_ZYLLXXX_SSBDZ <> (select T_ZYLLXXX_SSBDZ from t_sbsj_zyllxxx where T_ZYLLXXX_ID = xlId)
             and T_ZYLLXXX_ID in(
             select T_zyxllx_bid from t_Sbsj_Zyxllx where T_ZYXLLX_EID = xlId union
             select T_ZYXLLX_EID from t_Sbsj_Zyxllx where T_ZYXLLX_Bid = xlId
             ) order by T_ZYLLXXX_XLMC) where rowIndex = xlIndex;
    else
            select T_ZYLLXXX_XLMC into lxName from ( select T_ZYLLXXX_XLMC,rownum rowIndex from t_sbsj_zyllxxx  where T_ZYLLXXX_SSBDZ = (select T_ZYLLXXX_SSBDZ from t_sbsj_zyllxxx where T_ZYLLXXX_ID = xlId)
             and T_ZYLLXXX_ID in(
             select T_zyxllx_bid from t_Sbsj_Zyxllx where T_ZYXLLX_EID = xlId union
             select T_ZYXLLX_EID from t_Sbsj_Zyxllx where T_ZYXLLX_Bid = xlId
             ) order by T_ZYLLXXX_XLMC) where rowIndex = xlIndex;
      end if;
    return(lxName);
end fun_GetLianLuoxianName;


/
--------------------------------------------------------
--  DDL for Function FUN_GETLLXNAME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETLLXNAME" (xlId in varchar2) return varchar2 is
lxName varchar2(100);
begin
  select t_zyllxxx_xlmc into lxName from t_sbsj_zyllxxx where t_zyllxxx_id=xlId;
    return(lxName);
end fun_GetLLxName;


/
--------------------------------------------------------
--  DDL for Function FUN_GETMPBBNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETMPBBNAMEBYID" (Ids in varchar2) return varchar2 is
--铭牌变比
names varchar2(100);
begin
  select t_Mpbb_Name into names from t_Jcsj_Mpbb where t_Mpbb_id = Ids;
  return(names);
end Fun_getMpbbNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETMYXLIDBYOPENXLIDANDDYDJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETMYXLIDBYOPENXLIDANDDYDJ" (openXlId varchar2,xlDydjId varchar2) return varchar2 is
myXlId varchar2(200);
dyName varchar2(200);
begin
select T_dycs_name into dyName from T_jcsj_cs_dycs where T_dycs_id = xlDydjId;

 if(dyName='10')

  then
    select mysbId into myXlId from t_Sbguanlian where sbtype='中压线路' and opensbId = openXlId;
  else
    select mysbId into myXlId from t_Sbguanlian where sbtype='高压线路' and opensbId = openXlId;
  end if;


  return(myXlId);
end fun_getMyxlIdByOpenXlIdAndDydj;


/
--------------------------------------------------------
--  DDL for Function FUN_GETPDZZLXNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETPDZZLXNAMEBYID" (ids in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select t_gypdzzlx_name into ret_result from T_JCSJ_GYPDZZLX where t_gypdzzlx_id = ids;
  return(ret_result);
end fun_getpdzzlxnamebyid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQITAFHBYBYQIDANDSHIJIAN
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQITAFHBYBYQIDANDSHIJIAN" (c_byqId in varchar2, shijian in varchar2) return number is
  qitaFuHe number;
begin
    select nvl(sum(yougong),0) into qitaFuHe from t_Yx_Jiagong_Byq where jgnf = shijian and cmtype='高' and tqtype= '自身'
    and byqId in (select T_BYQXX_ID from T_sbsj_byqxx where T_BYQXX_BDZID =
  (select T_BYQXX_BDZID from T_sbsj_byqxx where T_BYQXX_ID = c_byqId) and T_BYQXX_ID <> c_byqId);
  return(qitaFuHe);
end fun_GetQiTaFhByByqIdAndShiJian;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQITARONGLIANGBYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQITARONGLIANGBYBYQID" (c_byqId in varchar2) return number is
qitaRl number;
begin
select nvl(sum(T_BYQXX_BDRL),0) into qitaRl   from T_sbsj_byqxx where T_BYQXX_BDZID =
  (select T_BYQXX_BDZID from T_sbsj_byqxx where T_BYQXX_ID = c_byqId)
  and T_BYQXX_ID <> c_byqId;
  return(qitaRl);
end fun_GetQiTaRongLiangByByqId;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQSHYDL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQSHYDL" (nf in varchar2) return varchar2 is
--全社会用电量
re_region varchar2(50);
begin
select sum(T_Lsfhdl_ALLSOCIETYYDL)  into re_region from T_JcSj_yx_Lsfhdl where T_Lsfhdl_YEAR <=nf  ;
 return re_region;

end fun_GetQshYdl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQY_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQY_BYBYQID" (byqId in varchar2) return varchar2 is
qy varchar2(100);
begin
  select T_GDFQ_NAME into qy from T_JCSJ_FQ_GDFQ where T_GDFQ_ID = (select T_BDZXX_GDQYID from T_SBSJ_BDZXX where T_BDZXX_ID=(select T_BYQXX_BDZID from T_SBSJ_BYQXX where T_BYQXX_ID = byqId));
  return(qy);
end FUN_GETQY_BYBYQID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQY_BYGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQY_BYGYXLID" (xlId in varchar2) return varchar2 is
qy varchar2(100);
begin
  select T_GDFQ_NAME into qy from T_JCSJ_FQ_GDFQ where T_GDFQ_ID = (select T_GYXLXX_SSQY from T_SBSJ_GYXLXX where T_GYXLXX_ID = xlId);
  return(qy);
end FUN_GETQY_BYGYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQY_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQY_BYZYXLID" (xlId in varchar2) return varchar2 is
qy varchar2(100);
begin
  select T_GDFQ_NAME into qy from T_JCSJ_FQ_GDFQ where T_GDFQ_ID = (select T_ZYLLXXX_SSQY from T_SBSJ_ZYLLXXX where T_ZYLLXXX_ID = xlId);
  return(qy);
end FUN_GETQY_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETQYNAMEBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETQYNAMEBYBDZID" (bdzId in varchar2) return varchar2 is
qy varchar2(100);
begin
  select T_GDFQ_NAME into qy from T_JcSj_fq_Gdfq where T_GDFQ_ID = (select T_BDZXX_GDQYID from T_SbSj_Bdzxx where T_Bdzxx_ID = bdzId);
  return(qy);
end FUN_GETQYNAMEBYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETSYFS_BYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETSYFS_BYBDZID" (bdzId in varchar2) return varchar2 is
syfs varchar2(50);
begin
  select T_Syfs_NAME into syfs from T_JCSJ_SYFS where T_Syfs_ID = (select T_Bdzxx_Syfs from T_SbSj_Bdzxx where T_Bdzxx_ID = bdzId);
  return(syfs);
end fun_getsyfs_bybdzid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETSYFS_BYBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETSYFS_BYBYQID" (byqId in varchar2) return varchar2 is
syfs varchar2(50);
begin
  select T_Syfs_NAME into syfs from T_JCSJ_SYFS where T_Syfs_ID = (select T_Bdzxx_Syfs from T_SbSj_Bdzxx where T_Bdzxx_ID = (select T_BYQXX_BDZID from T_SBSJ_BYQXX where T_BYQXX_ID = byqId));
  return(syfs);
end fun_getsyfs_bybyqid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETSYFS_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETSYFS_BYZYXLID" (xlId in varchar2) return varchar2 is
syfs varchar2(50);
begin
  select T_Syfs_NAME into syfs from T_JCSJ_SYFS where T_Syfs_ID = (select T_zyllxxx_Syfs from t_sbsj_zyllxxx where T_zyllxxx_ID = xlId);
  return(syfs);
end fun_getsyfs_byzyxlid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETSYFSNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETSYFSNAMEBYID" (Ids in varchar2) return varchar2 is
--使用方式
names varchar2(100);
begin
  select t_Syfs_Name into names from t_Jcsj_Syfs where t_Syfs_id = Ids;
  return(names);
end Fun_getSyfsNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETTYFSNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETTYFSNAMEBYID" (Ids in varchar2) return varchar2 is
--调压方式
names varchar2(100);
begin
  select t_Tyfs_Name into names from t_Jcsj_Tyfs where t_Tyfs_id = Ids;
  return(names);
end Fun_getTyfsNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETUSERNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETUSERNAMEBYID" (Us_Id in varchar2) return varchar2 is
  ret_result varchar2(100);
begin
  select U_Name into ret_result from t_Sys_User where U_Id = Us_Id;
  return(ret_result);
end Fun_GetUserNameById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETXLIDBYJGIDANDDYDJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETXLIDBYJGIDANDDYDJ" (openJgId varchar2,dydjName varchar2) return varchar2 is
myXlId varchar2(200);
countIndex number;
begin
    select count(*) into countIndex from ems.bay_device_info@jkdata  where  bay_id= openJgId ;
    if(countIndex>0)
    then
      if(dydjName='10')
      then
        --查询中压线路Id
        select b.mysbid into myXlId  from (select device_id from ems.bay_device_info@jkdata where bay_id= openJgId) a
        inner join t_Sbguanlian b on a.device_id=b.opensbid where  b.sbtype='中压线路' and rownum=1;
        else
          --查询高压线路Id
          select b.mysbid into myXlId from(
select acln_id from ems.acln_device@jkdata where acln_id in (select acln_id from ems.acln_dot@jkdata
where acln_dot_id in (select device_id from ems.bay_device_info@jkdata where bay_id=openJgId))) t
inner join t_sbguanlian b on t.acln_id=b.opensbid where b.sbtype='高压线路' and rownum = 1;

          end if;
      else
        return '';
        end if;

  return(myXlId);
end fun_getXlIdByJgIDAndDydj;


/
--------------------------------------------------------
--  DDL for Function FUN_GETXLIDBYJGIDANDLX
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETXLIDBYJGIDANDLX" (openJgId varchar2,dydjName varchar2) return varchar2 is
myXlId varchar2(200);
countIndex number;
begin
    select count(*) into countIndex from ems.bay_device_info@jkdata  where  bay_id= openJgId ;
    if(countIndex>0)
    then
      if(dydjName='10')
      then
        --查询中压线路Id
        select b.mysbid into myXlId from (select device_id from ems.bay_device_info@jkdata where bay_id= openJgId) a
        inner join t_Sbguanlian b on a.device_id=b.opensbid where  b.sbtype='中压线路' and b.mysbid <> ' ' and rownum=1;
        else
          --查询高压线路Id
          select b.mysbid into myXlId from(
select acln_id from ems.acln_device@jkdata where acln_id in (select acln_id from ems.acln_dot@jkdata
where acln_dot_id in (select device_id from ems.bay_device_info@jkdata where bay_id=openJgId))) t
inner join t_sbguanlian b on t.acln_id=b.opensbid where b.sbtype='高压线路' and b.mysbid <> ' '  and rownum=1;

          end if;
      else
        return '';
        end if;

  return(myXlId);
end fun_getXlIdByJgIDAndLX;


/
--------------------------------------------------------
--  DDL for Function FUN_GETXZGYBDZTSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETXZGYBDZTSBYID" (gyghId in varchar2) return number is
bdzts number;
begin
  select SUM(T_GYBDZ_BDZS) into bdzts from T_DWGHGL_GYBDZ where t_dwghgl_id=gyghId;
  return(bdzts);
end Fun_GETXZGYBDZTSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETYWDW_BYZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETYWDW_BYZYXLID" (xlId in varchar2) return varchar2 is
ywdw varchar2(100);
begin
  select T_SYS_DWMZ into ywdw from T_SYS_YWDW where T_SYS_ID = (select T_ZYLLXXX_YWDW from T_SBSJ_ZYLLXXX where T_ZYLLXXX_ID = xlId);
  return(ywdw);
end FUN_GETYWDW_BYZYXLID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETYWDWBYBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETYWDWBYBDZID" (bdzId in varchar2) return varchar2 is
ywdw varchar2(100);
begin
  select T_Sys_Dwmz into ywdw from T_Sys_Ywdw where T_Sys_Id = (select T_BDZXX_YWDW from T_SbSj_Bdzxx where T_Bdzxx_ID = bdzId);
  return(ywdw);
end FUN_GETYWDWBYBDZID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETYWDWNAMEBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETYWDWNAMEBYID" (ywdwId in varchar2) return varchar2 is
YWDW varchar2(36);
begin
  select t_sys_dwmz into YWDW from t_sys_ywdw where t_sys_id=ywdwId;
  return(YWDW);
end FUN_GETYWDWNAMEBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZDFSSK_BYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZDFSSK_BYXLID" (xlids in varchar2,nf in varchar2) return varchar2 is
--AB侧最大时刻
req_requper varchar2(50);
begin
  select t.zdfhfssk into req_requper from (select * from t_yx_jiagong_jiaoliuxl where
   xlid=xlids and jgnf=nf order by yougong desc) t where rownum<2;
  --select t_dxcs_aqdlxz into aqdl from t_jcsj_cs_dxcs where t_dxcs_dxid=dxid;
  --req_requper:=round(sqrt(3)*aqdl*to_number(dydjName)*0.95/1000);

  return(req_requper);
end fun_getZdfssk_byxlid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZDYG_BYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZDYG_BYXLID" (xlids in varchar2,nf in varchar2) return varchar2 is
--AB侧最大有功
req_requper number;
aqdl number;
begin
  select t.yougong into req_requper from (select * from t_yx_jiagong_jiaoliuxl where
   xlid=xlids and jgnf=nf order by yougong desc) t where rownum<2;
  --select t_dxcs_aqdlxz into aqdl from t_jcsj_cs_dxcs where t_dxcs_dxid=dxid;
  --req_requper:=round(sqrt(3)*aqdl*to_number(dydjName)*0.95/1000);

  return(req_requper);
end fun_getZdyg_byxlid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZDYGID_BYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZDYGID_BYXLID" (xlids in varchar2,nf in varchar2) return varchar2 is
--AB侧最大时刻
req_requper varchar2(50);
begin
  select t.jgid into req_requper from (select * from t_yx_jiagong_jiaoliuxl where
   xlid=xlids and jgnf=nf order by yougong desc) t where rownum<2;
  --select t_dxcs_aqdlxz into aqdl from t_jcsj_cs_dxcs where t_dxcs_dxid=dxid;
  --req_requper:=round(sqrt(3)*aqdl*to_number(dydjName)*0.95/1000);

  return(req_requper);
end fun_getZdygid_byxlid;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZHDYHGL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZHDYHGL" (cnw in varchar2 ,nf in varchar2) return varchar2 is
--综合电压合格率城农网
re_region varchar2(50);
begin

select round(sum(T_ZYPWYXZB_ZHDYHGL)/count(*),2)  into re_region from T_JCSJ_ZYPWYXZB where T_ZYPWYXZB_YEAR <=nf and T_ZYPWYXZB_CNW=(select t_cnw_id from t_jcsj_cnw where t_cnw_name=cnw) ;
 return re_region;

end fun_GetZhdyhgl;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZHXSL10
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZHXSL10" (nf in varchar2) return varchar2 is
--10千伏及以下综合线损率(%)
re_region varchar2(50);
begin
select round(sum(T_ZYPWYXZB_XSL10)/count(*),2)  into re_region from T_JCSJ_ZYPWYXZB where T_ZYPWYXZB_YEAR <=nf  ;
 return re_region;

end fun_GetZhXsl10;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZHXSL110
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZHXSL110" (nf in varchar2) return varchar2 is
--110千伏及以下综合线损率(%)
re_region varchar2(50);
begin
select round(sum(T_ZYPWYXZB_XSL110)/count(*),2)  into re_region from T_JCSJ_ZYPWYXZB where T_ZYPWYXZB_YEAR <=nf  ;
 return re_region;

end fun_GetZhXsl110;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZRK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZRK" (nf in varchar2) return varchar2 is
--总人口
re_region varchar2(50);
begin
select sum(T_DQGKXX_NMZRK)  into re_region from T_JCSJ_DQGKXX where T_DQGKXX_YEAR <=nf  ;
 return re_region;

end fun_GetZrk;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYGHGZQRLBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYGHGZQRLBYID" (zyghId in varchar2) return number is
pbrl number;
begin
  select SUM(T_DWGHGL_ZRL) into pbrl from T_DWGHGL_ZYXL where T_ZYGHK_ID=zyghId;
  return(pbrl);
end FUN_GETZYGHGZQRLBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYGHZTSBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYGHZTSBYID" (zyghId in varchar2) return number is
bdztz number;
begin
  select SUM(T_DWGHGL_ZTS) into bdztz from T_DWGHGL_ZYXL where T_ZYGHK_ID=zyghId;
  return(bdztz);
end FUN_GETZYGHZTSBYID;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXL_CNWANDSYFSCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXL_CNWANDSYFSCOUNT" (cnw in varchar2,syfs in varchar2,nf in varchar2) return varchar2 is
--10kV城、农网对应公用线路条数
re_region varchar2(50);
begin

select count(*) into re_region from t_sbsj_zyllxxx where t_sbsj_zyllxxx.t_zyllxxx_ynw=(select t_cnw_id from t_jcsj_cnw where t_cnw_name=cnw)
 and t_zyllxxx_syfs=(select t_syfs_id from t_jcsj_syfs where t_syfs_name=syfs) and to_number(substr(t_zyllxxx_tysj,0,4)) <= nf and  t_sbsj_zyllxxx.t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxl_CnwandsyfsCount;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXL_CNWCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXL_CNWCOUNT" (cnw in varchar2,nf in varchar2) return varchar2 is
--10kV城、农网对应线路条数
re_region varchar2(50);
begin

select count(*) into re_region from t_sbsj_zyllxxx where t_sbsj_zyllxxx.t_zyllxxx_ynw=(select t_cnw_id from t_jcsj_cnw where t_cnw_name=cnw) 
and t_zyllxxx_tysj like '%'||nf||'%' and  t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxl_CnwCount;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXL_ZDFHFSSK
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXL_ZDFHFSSK" (zyxlId in varchar2,selYear in varchar2) return varchar2 is
  --中压线路最大负荷发生时刻
  ret_result varchar2(100);
begin
   if(selYear='')
   then
     select ZDFHFSSK  into ret_result from (select sum(s.yougong) zdfh,
     Min(s.ZDFHFSSK) ZDFHFSSK , jgNf from T_YX_JIAGONG_ZHONGYAXL s where
 xlId =zyxlId group by jgnf order by Zdfh desc) where rownum=1;
   else
  select ZDFHFSSK into ret_result from (select sum(s.yougong) zdfh,Min(s.ZDFHFSSK) ZDFHFSSK, jgnf from T_YX_JIAGONG_ZHONGYAXL s
   where xlId =zyxlId and substr(jgnf,0,4) = SelYear
group by jgnf order by Zdfh desc) where rownum=1 ;
  end if;
  return ret_result;
end FUN_GETZYXL_ZDFHFSSK;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLCOUNT" (nf in varchar2) return varchar2 is
--10kV线路条数
re_region varchar2(50);
begin

select count(*) into re_region from t_sbsj_zyllxxx where to_number(substr(t_zyllxxx_tysj,0,4)) <= nf  and t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxlCount;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLHWG
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLHWG" (nf in varchar2) return varchar2 is
--10kV环网柜数
re_region varchar2(50);
begin

select sum(t_zyllxxx_hwgzs) into re_region from t_sbsj_zyllxxx where to_number(substr(t_zyllxxx_tysj,0,4)) <= nf  and t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxlHwg;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLTYPE
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLTYPE" (Ids in varchar2) return varchar2 is
--中压线路类型
xlmc varchar2(50);
cishu number;
begin
  select count(t_ZyxlLs_id) into cishu from T_SbBj_ZyxlLs where T_ZYLLXXX_ID = ids;
  if(cishu=0)
  then
    return '新建';
  else
    return '改造';
  end if;
end fun_GetZyxlType;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLXGCOUNTBYID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLXGCOUNTBYID" (zyxlId in Varchar2) return number is
  xg_count number ;
begin
  select count(T_ZyxlLs_Id) into xg_count from T_SBBJ_ZYXLLS where T_ZYLLXXX_ID = zyxlId;
  return(xg_count);
end fun_GetZyxlXgCountById;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLZGXPJCD
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLZGXPJCD" (nf in varchar2) return varchar2 is
--10kV柱上开关数
re_region varchar2(50);
begin

select round(sum(t_sbsj_zyllxxx.t_zyllxxx_zczgx)/count(*),2) into re_region from t_sbsj_zyllxxx where to_number(substr(t_zyllxxx_tysj,0,4)) <= nf  and t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxlZgxPjcd;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYXLZSKG
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYXLZSKG" (nf in varchar2) return varchar2 is
--10kV柱上开关数
re_region varchar2(50);
begin

select sum(t_zyllxxx_zskgzs) into re_region from t_sbsj_zyllxxx where to_number(substr(t_zyllxxx_tysj,0,4)) <= nf  and t_zyllxxx_zt='运行';
  return re_region;

end fun_GetZyxlZskg;


/
--------------------------------------------------------
--  DDL for Function FUN_GETZYZNZJLLXBYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GETZYZNZJLLXBYXLID" (Ids in varchar2) return varchar2 is
--站内、站间联络线
xlmc varchar2(100);
begin
  select T_ZYLLXXX_XLMC into xlmc from T_SBSJ_ZYLLXXX where T_ZYLLXXX_ID = Ids;
  return(xlmc);

end fun_GetZyZnzjllxByXlId;


/
--------------------------------------------------------
--  DDL for Function FUN_GYXLJFDLCOUNT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."FUN_GYXLJFDLCOUNT" (xlid in varchar2,nf in varchar2) return varchar2 is
 --高压线路积分电量
  ret_result varchar2(100);
begin
select round(sum(t_jfdl_zongzhi),2) into ret_result from t_yx_gyxljfdl where t_jfdl_xlid=xlid
and t_jfdl_type='月' and substr(t_jfdl_data,0,4)=nf;

  return ret_result;
end fun_gyxlJfdlCount;


/
--------------------------------------------------------
--  DDL for Function GET_BDZ_ZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_BDZ_ZDFH" (bdzId in varchar2,selYear in varchar2) return varchar2 is
  --变电站最大负荷
  ret_result varchar2(100);
  str varchar2(10);
begin
   select max(yougong) into ret_result from t_Yx_Jiagong_Bdz where Substr(JGNF, 0,4)=selYear and byqId=bdzId;
   select substr(ret_result,0,1) into str from dual;
    if(str = '.')
    then
      ret_result:='0'||to_char(ret_result);
      end if;
  return ret_result;
end get_bdz_Zdfh;


/
--------------------------------------------------------
--  DDL for Function GET_BDZ_ZDFZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_BDZ_ZDFZL" (bdzId in varchar2,selYear in varchar2) return varchar2 is
  --变电站最大负载率
  ret_result varchar2(100);
begin
   select max(FUN_GET_FZL_BYBDZID(jgId)) into ret_result from t_Yx_Jiagong_Bdz where Substr(JGNF, 0,4)=selYear and byqId=bdzId;
  return ret_result;
end get_bdz_Zdfzl;


/
--------------------------------------------------------
--  DDL for Function GET_BYQ_ZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_BYQ_ZDFH" (byqIds in varchar2,selYear in varchar2) return varchar2 is
  --变压器最大负荷
  ret_result varchar2(100);
  str varchar2(10);
begin
   select max(yougong) into ret_result from t_Yx_Jiagong_Byq where Substr(JGNF, 0,4)=selYear and byqId=byqIds;
   select substr(ret_result,0,1) into str from dual;
    if(str = '.')
    then
      ret_result:='0'||to_char(ret_result);
      end if;
  return ret_result;
end get_byq_Zdfh;


/
--------------------------------------------------------
--  DDL for Function GET_BYQ_ZDFZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_BYQ_ZDFZL" (bdzId in varchar2,selYear in varchar2) return varchar2 is
  --变压器最大负载率
  ret_result varchar2(100);
begin
   select max(FUN_GET_FZL_BYBYQID(jgId)) into ret_result from t_Yx_Jiagong_Byq where Substr(JGNF, 0,4)=selYear and byqId=bdzId;
  return ret_result;
end get_byq_Zdfzl;


/
--------------------------------------------------------
--  DDL for Function GET_GYXL_ZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_GYXL_ZDFH" (gyxlId in varchar2,selYear in varchar2) return varchar2 is
  --高压线路最大负荷
  ret_result varchar2(100);
  str varchar2(10);
begin
   select max(yougong) into ret_result from t_yx_jiagong_jiaoliuxl where Substr(JGNF, 0,4)=selYear and xlId=gyxlId;
  select substr(ret_result,0,1) into str from dual;
    if(str = '.')
    then
      ret_result:='0'||to_char(ret_result);
      end if;
  return ret_result;
end get_gyxl_Zdfh;


/
--------------------------------------------------------
--  DDL for Function GET_GYXL_ZDFZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_GYXL_ZDFZL" (gyxlId in varchar2,selYear in varchar2) return varchar2 is
  --高压线路最大负载率
  ret_result varchar2(100);
begin
   select max(FUN_GET_FZL_BYGYXLID(jgId)) into ret_result from t_yx_jiagong_jiaoliuxl where Substr(JGNF, 0,4)=selYear and xlId=gyxlId;
  return ret_result;
end get_gyxl_Zdfzl;


/
--------------------------------------------------------
--  DDL for Function GET_NEWYEAR
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_NEWYEAR" (yaer in varchar2) return varchar2 is
  ret_result varchar2(20);
begin
  select TO_CHAR(SYSDATE,'yyyy')-1 into ret_result from dual;
  return ret_result;
end get_NewYear;


/
--------------------------------------------------------
--  DDL for Function GET_ZYXL_ZDFH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_ZYXL_ZDFH" (zyxlId in varchar2,selYear in varchar2) return varchar2 is
  --中压线路最大负荷
  ret_result varchar2(100);
  str varchar2(10);
begin
   select max(yougong) into ret_result from t_Yx_Jiagong_Zhongyaxl where Substr(JGNF, 0,4)=selYear and xlId=zyxlId;
  select substr(ret_result,0,1) into str from dual;
    if(str = '.')
    then
      ret_result:='0'||to_char(ret_result);
      end if;
  return ret_result;
end get_zyxl_Zdfh;


/
--------------------------------------------------------
--  DDL for Function GET_ZYXL_ZDFZL
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GET_ZYXL_ZDFZL" (zyxlId in varchar2,selYear in varchar2) return varchar2 is
  --中压线路最大负载率
  ret_result varchar2(100);
begin
   select max(FUN_GET_FZL_BYZYXLID(jgId)) into ret_result from t_Yx_Jiagong_Zhongyaxl where Substr(JGNF, 0,4)=selYear and xlId=zyxlId;
  return ret_result;
end get_zyxl_Zdfzl;


/
--------------------------------------------------------
--  DDL for Function GETBDZID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETBDZID" (openBdzName in varchar2)
 return varchar2 is
 ret_result varchar(200);
 countIndex number;
begin

    select  count(*) into CountIndex from t_Sbsj_Bdzxx where
replace(openBdzName,'德阳.','') like '%'||replace(replace(replace(replace(replace(replace(replace(T_BDZXX_NAME,'变电站'),'电铁'),'开关站'),'水泥'),'电'),'站'),' ')||'%';
    if(CountIndex=0)
    then
     ret_result:='';
     else
           select  T_BDZXX_ID into ret_result from t_Sbsj_Bdzxx where
replace(openBdzName,'德阳.','') like '%'||replace(replace(replace(replace(replace(replace(replace(T_BDZXX_NAME,'变电站'),'电铁'),'开关站'),'水泥'),'电'),'站'),' ')||'%' and rownum<2;
       end if;


    return ret_result;
end GetBdzId;


/
--------------------------------------------------------
--  DDL for Function GETBDZMAXFUHE
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETBDZMAXFUHE" (f_acId in char,shiJian in char)
 return varchar2 is
 ret_result varchar(100);
 strsql varchar(4000);
 tableName varchar(100);
 ziDuan varchar(100);
 xunHuanSql varchar(4000); --中间循环的Sql
cursor myCur is
  select history_table_name,history_column_name from ems.svr_yc_sample_define@jkdata where yc_id in(
  select  '0'||TRWD_ID||'0050' from ems.trwd_device@jkdata where fac_id = f_acId and TRWD_Name like '%高%');

begin

--拼接
          strsql :='select nvl(zhi,''0'')||''#''||to_char(occur_time,''yyyy-mm-dd hh24:mi:ss'') from
(select occur_time,sum(cur) zhi from  ( ';
         xunHuanSql:='';
       for aaa in myCur loop
           tableName := aaa.history_table_name;
           ziDuan := aaa.history_column_name;
           --select  occur_time,cur_045 cur from  ems.yc_hs_000062@jkdata where to_char(occur_time,'yyyy-mm')='2014-11'
           xunHuanSql:= xunHuanSql || ' select  occur_time,'|| ziDuan
           ||' cur from  ems.'||tableName||'@jkdata where to_char(occur_time,''yyyy-mm'')='''||shiJian||''' union';
           end loop;
           --xunHuanSql去掉最后一个union
           xunHuanSql := subStr(xunHuanSql,1,length(xunHuanSql)-5);
           --拼接
           strsql := strsql || xunHuanSql;

           strsql := strsql || ' ) group by occur_time ) where zhi = (select max(zhi) from( select occur_time,sum(cur) zhi from (';
           strsql := strsql || xunHuanSql;
           strsql := strsql || ') group by occur_time)) and rownum<2 ';

  execute immediate strsql into ret_result;
    return ret_result;
end GetBdzMaxFuHe;


/
--------------------------------------------------------
--  DDL for Function GETBDZMAXFUHEBYCM
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETBDZMAXFUHEBYCM" (f_acId in char,shiJian in char,cmLeiXing in varchar2)
 return varchar2 is
 ret_result varchar(100);
 strsql varchar(4000);
 tableName varchar(100);
 ziDuan varchar(100);
 xunHuanSql varchar(4000); --中间循环的Sql
cursor myCur is
  select history_table_name,history_column_name from ems.svr_yc_sample_define@jkdata where yc_id in(
  select  '0'||TRWD_ID||'0050' from ems.trwd_device@jkdata where fac_id = f_acId and TRWD_Name like '%' || cmLeiXing ||'%');

begin

--拼接
          strsql :='select nvl(zhi,''0'')||''#''||to_char(occur_time,''yyyy-mm-dd hh24:mi:ss'') from
(select occur_time,sum(cur) zhi from  ( ';
         xunHuanSql:='';
       for aaa in myCur loop
           tableName := aaa.history_table_name;
           ziDuan := aaa.history_column_name;
           --select  occur_time,cur_045 cur from  ems.yc_hs_000062@jkdata where to_char(occur_time,'yyyy-mm')='2014-11'
           xunHuanSql:= xunHuanSql || ' select  occur_time,'|| ziDuan
           ||' cur from  ems.'||tableName||'@jkdata where to_char(occur_time,''yyyy-mm'')='''||shiJian||''' union';
           end loop;
           --xunHuanSql去掉最后一个union
           xunHuanSql := subStr(xunHuanSql,1,length(xunHuanSql)-5);
           --拼接
           strsql := strsql || xunHuanSql;

           strsql := strsql || ' ) group by occur_time ) where zhi = (select max(zhi) from( select occur_time,sum(cur) zhi from (';
           strsql := strsql || xunHuanSql;
           strsql := strsql || ') group by occur_time)) and rownum<2 ';

  execute immediate strsql into ret_result;
    return ret_result;
end GetBdzMaxFuHeByCm;


/
--------------------------------------------------------
--  DDL for Function GETBYQID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETBYQID" (openByqName in varchar2,bdzid in varchar2)
 return varchar2 is
 ret_result varchar2(200);
 countIndex number;
 myBdzid varchar2(100);
begin

select count(*) into CountIndex  from t_sbguanlianlinshi where sbtype ='变电站' and opensbid =bdzid;
    if(CountIndex=0)
    then
     ret_result:='';
     else
 select mysbid into myBdzid  from t_sbguanlianlinshi where sbtype ='变电站' and opensbid =bdzid;
 CountIndex:=0;
     select  count(*) into CountIndex from t_Sbsj_Byqxx where  T_BYQXX_BDZID =myBdzid and T_BYQXX_NAME like '%'||replace(openByqName,'变压器','')||'%';
    if(CountIndex=0)
    then
     ret_result:='';
     else
--C8206ACB652248BD92B1A598B1521930
   select T_BYQXX_ID into ret_result from t_Sbsj_Byqxx where T_BYQXX_BDZID =myBdzid and T_BYQXX_NAME like '%'||replace(openByqName,'变压器','')||'%' and rownum<2;


--      select T_BYQXX_ID  from t_Sbsj_Byqxx where T_BYQXX_BDZID ='C8206ACB652248BD92B1A598B1521930' and T_BYQXX_NAME like '%2#%';
       end if;
       end if;





    return ret_result;

end GetbyqId;


/
--------------------------------------------------------
--  DDL for Function GETFZLQJ
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETFZLQJ" (qjlx char,fhz number) return varchar2 is
  ret_result varchar(200) ;
begin
  select qj_qs||'%~~'||qj_js||'%' into ret_result from
   fzlqj where trim(qj_lb)=qjlx and qj_qs<=fhz and  fhz<qj_js;
  return ret_result;
  end GetFzlqj;


/
--------------------------------------------------------
--  DDL for Function GETGYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETGYXLID" (openGyxlName in varchar2)
 --根据op3000线路名称获取对应我们系统的线路Id
 return varchar2 is
 ret_result varchar(200);
 countIndex number;
begin

    select  count(*) into CountIndex from T_SBSJ_GYXLXX where
 T_GYXLXX_XLMC like '%'||replace(replace(replace(replace(replace(replace(replace(replace(replace(openGyxlName,'500kV.')
 ,'220kV.'),'110kV.'),'35kV.'),'10kV.'),'110kV'),'10kV.'),'支线'),'110.kV.')||'%';
    if(CountIndex=0)
    then
     ret_result:='';
     else
         select T_GYXLXX_ID into ret_result from T_SBSJ_GYXLXX where
 T_GYXLXX_XLMC like '%'||replace(replace(replace(replace(replace(replace(replace(replace(replace(openGyxlName,'500kV.')
 ,'220kV.'),'110kV.'),'35kV.'),'10kV.'),'110kV'),'10kV.'),'支线'),'110.kV.')||'%' and rownum<2;
     end if;

    return ret_result;
end GetGyxlId;


/
--------------------------------------------------------
--  DDL for Function GETGYXLIDBYLD
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETGYXLIDBYLD" (openZyxlName in varchar2)
 --根据op3000线路名称获取对应我们系统的线路Id
 return varchar2 is
 ret_result varchar(200);
begin

    select T_GYXLXX_ID into ret_result from T_SBSJ_GYXLXX where
 T_GYXLXX_XLMC like '%'||replace(replace(regexp_replace(openZyxlName,'[0-9]+',''),'kV.'),'负荷')||'%' and rownum<2;

    return ret_result;
end GetGyxlIdByLd;


/
--------------------------------------------------------
--  DDL for Function GETMAXFUHE
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETMAXFUHE" (tableName in char,ziDuan in char,shiJian in char,flagType in char)
 return varchar2 is
 ret_result varchar(200);
 strsql varchar(5000);
 occurTime varchar(20);

begin
  if(flagType='0')
  then
    --提取原始hs结果表（效率低）
     strsql :=' select round(to_char(t.Cur_001,''fm999990.99999''),6)||''#''||to_char( occur_time,''yyyy-mm-dd hh24:mi:ss'')
   from ems.yc_hs_000081@jkdata t,
(select  abs(max(Cur_001)) zdz  from    ems.yc_hs_000081@jkdata
  where to_char(occur_time,''yyyy-mm'')=''2012-03''  ) zdz
  where   abs(t.Cur_001)=zdz.zdz
  and to_char(t.occur_time,''yyyy-mm'')=''2012-03''
  and rownum<2';
  strsql := replace(strsql,'ems.yc_hs_000081@jkdata',tableName);
    else
     --提取stat结果表
     occurTime := replace('occur_time_001','_001',replace(lower(ziDuan),'cur',''));
     strsql :='select round(to_char(t.Cur_001,''fm999990.99999''),6)||''#''||to_char( occur_time_001,''yyyy-mm-dd hh24:mi:ss'')
   from ems.yc_hs_000081@jkdata t,
(select  abs(max(Cur_001)) zdz  from    ems.yc_hs_000081@jkdata
  where to_char(statistics_date,''yyyy-mm'')=''2012-03'' and statistics_flag=''1'' ) zdz
  where   abs(t.Cur_001)=zdz.zdz
  and to_char(t.statistics_date,''yyyy-mm'')=''2012-03''
  and rownum<2 and statistics_flag = ''1''';
    strsql := replace(strsql,'Cur_001',ziDuan);
    strsql := replace(strsql,'occur_time_001',occurTime);
    strsql := replace(strsql,'ems.yc_hs_000081@jkdata',replace(tableName,'_hs_','_stat_'));
      end if;
  strsql := replace(strsql,'Cur_001',ziDuan);
  -- strsql := replace(strsql,'occur_time_002','occur_time'||substr(t_zd,4,4));
  strsql := replace(strsql,'2012-03',shiJian);

 execute immediate strsql into ret_result;
    return ret_result;
end GetMaxFuHe;


/
--------------------------------------------------------
--  DDL for Function GETMAXFUHENOSHOWTIME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETMAXFUHENOSHOWTIME" (tableName in char,ziDuan in char,shiJian in char)
 return varchar2 is
 ret_result varchar(200);
 strsql varchar(5000);

begin

     --提取stat结果表
     strsql :='select  abs(max(Cur_001)) zdz  from  ems.yc_hs_000081@jkdata
  where to_char(statistics_date,''yyyy-mm'')=''2012-03'' and statistics_flag=''1''';
    strsql := replace(strsql,'Cur_001',ziDuan);
    strsql := replace(strsql,'ems.yc_hs_000081@jkdata',replace(tableName,'_hs_','_stat_'));

  strsql := replace(strsql,'2012-03',shiJian);

 execute immediate strsql into ret_result;
    return ret_result;
end GetMaxFuHeNoShowTime;


/
--------------------------------------------------------
--  DDL for Function GETYAOGANZHIBYYOUGONGTIME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETYAOGANZHIBYYOUGONGTIME" (tableName in char,ziDuan in char,shiJian in char)
 return varchar2 is
 ret_result varchar(100);
 strsql varchar(500);

begin
  strsql :='select  round(max(Cur_001),6) zdz  from  EMS.yc_hs_500012@jkdata
  where OCCUR_TIME = to_date(''2011-08-26  11:44:00'',''yyyy-MM-dd  hh24:mi:ss'')
  and rownum<2';


  strsql := replace(strsql,'Cur_001',ziDuan);
  -- strsql := replace(strsql,'occur_time_002','occur_time'||substr(t_zd,4,4));
  strsql := replace(strsql,'2011-08-26  11:44:00',shiJian);
  strsql := replace(strsql,'EMS.yc_hs_500012@jkdata',tableName);
  execute immediate strsql into ret_result;
 -- select max(t_zd) into ret_result from '|t_name|'  ;
  return ret_result;
end GetYaoGanZhiByYouGongTime;


/
--------------------------------------------------------
--  DDL for Function GETZYXLID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."GETZYXLID" (openZyxlName in varchar2)
 return varchar2 is
 ret_result varchar(200);
 countIndex number;
begin

    select  count(*) into CountIndex from T_SBSJ_ZYLLXXX where
    openZyxlName like '%'||replace(replace(replace(T_ZYLLXXX_XLMC,'10kV'),'线路'),'#开关')||'%' or
    T_ZYLLXXX_XLMC like '%'||replace(replace(replace(replace( replace(openZyxlName,'10kV.'),'负荷'),'35kV.'),'110kV.'),' ')||'%';
    if(CountIndex=0)
    then
     ret_result:='';
     else
    select T_ZYLLXXX_ID into ret_result from T_SBSJ_ZYLLXXX where
    openZyxlName like '%'||replace(replace(replace(T_ZYLLXXX_XLMC,'10kV'),'线路'),'#开关')||'%' or
    T_ZYLLXXX_XLMC like '%'||replace(replace(replace(replace( replace(openZyxlName,'10kV.'),'负荷'),'35kV.'),'110kV.'),' ')||'%' and rownum<2;
     end if;

    return ret_result;

end GetZyxlId;


/
--------------------------------------------------------
--  DDL for Function JCMBGETIDBYNAME
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."JCMBGETIDBYNAME" (JcMb_Id in varchar2) return varchar2 is
  ret_result varchar2(36);
begin
  select T_JcMb_Name into ret_result from t_Jcsj_Jcmb where T_Jcmb_Id = JcMb_Id;
  return(ret_result);
end JcMbGetIdByName;


/
--------------------------------------------------------
--  DDL for Function STRSPLIT
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "SYSTEM"."STRSPLIT" (p_value varchar2, p_split varchar2 := '_')
--usage: select * from table(strsplit('1,2,3,4,5'))
 return type_split
  pipelined is
  v_idx       integer;
  v_str       varchar2(500);
  v_strs_last varchar2(4000) := p_value;

begin
  loop
    v_idx := instr(v_strs_last, p_split);
    exit when v_idx = 0;
    v_str       := substr(v_strs_last, 1, v_idx - 1);
    v_strs_last := substr(v_strs_last, v_idx + 1);
    pipe row(v_str);
  end loop;
  pipe row(v_strs_last);
  return;

end strsplit;


/
