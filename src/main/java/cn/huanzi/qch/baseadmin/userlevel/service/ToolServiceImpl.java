package cn.huanzi.qch.baseadmin.userlevel.service;

import cn.huanzi.qch.baseadmin.common.pojo.PageInfo;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.userlevel.mapper.AddressMapper;
import cn.huanzi.qch.baseadmin.userlevel.mapper.BankCardMapper;
import cn.huanzi.qch.baseadmin.userlevel.mapper.IdCardMapper;
import cn.huanzi.qch.baseadmin.userlevel.mapper.PhoneMapper;
import cn.huanzi.qch.baseadmin.userlevel.pojo.Address;
import cn.huanzi.qch.baseadmin.userlevel.service.ToolService;
import cn.huanzi.qch.baseadmin.util.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class ToolServiceImpl implements ToolService {
    private static final Logger log = LoggerFactory.getLogger(cn.huanzi.qch.baseadmin.userlevel.service.ToolServiceImpl.class);

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private IdCardMapper idCardMapper;

    @Autowired
    private BankCardMapper bankCardMapper;

    private static final String TRX_URL = "https://www.oklink.com/api/explorer/v1/tron/addresses/info/";

    private static final String ETH_URL = "https://www.oklink.com/api/explorer/v1/eth/addresses/";

    private static final String BSC_URL = "https://www.oklink.com/api/explorer/v1/bsc/addresses/";

    private static final String OK_ACCESS_KEY = "8fabf729-6947-464a-80e5-ca98c552b603";

    public Object addressQuery(String address, String chain) throws Exception {
        Map<String, Object> res = new HashMap<>();
        List<Address> list = new ArrayList<>();
        if (!StringUtils.isEmpty(address)) {
            String[] addressStr = address.split("\n");
            Set<String> addressSet = new HashSet<>(Arrays.asList(addressStr));
            List<String> addressList = new ArrayList<>(addressSet);
            List<Address> localAddressList = this.addressMapper.findByAddress(chain, addressList);
            Map<String, Address> localAddressMap = (Map<String, Address>)localAddressList.stream().collect(Collectors.toMap(e -> e.getAddress() + "_" + e.getChain(), Function.identity()));
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            List<Address> newAddressList = getAddressInfoNew(addressList, chain);
            if (!CollectionUtils.isEmpty(newAddressList))
                newAddressList.forEach(e -> {
                    e.setChain(chain);
                    e.setLabel((e.getLabel() == null) ? "" : e.getLabel());
                    e.setCurrBalance((e.getCurrBalance() == null) ? "0" : e.getCurrBalance());
                    e.setUsdtBalance((e.getUsdtBalance() == null) ? "0" : e.getUsdtBalance());
                });
            Map<String, Address> newAddressMap = (Map<String, Address>)newAddressList.stream().collect(Collectors.toMap(e -> e.getAddress() + "_" + e.getChain(), Function.identity()));
            for (String addressVal : addressList) {
                Address addressDTO = new Address();
                addressDTO.setAddress(addressVal);
                addressDTO.setChain(chain);
                if (newAddressMap.containsKey(addressVal + "_" + chain)) {
                    Address address2 = newAddressMap.get(addressVal + "_" + chain);
                    addressDTO.setLabel(address2.getLabel());
                    addressDTO.setCurrBalance(address2.getCurrBalance());
                    addressDTO.setUsdtBalance(address2.getUsdtBalance());
                } else if (localAddressMap.containsKey(addressVal + "_" + chain)) {
                    Address address2 = localAddressMap.get(addressVal + "_" + chain);
                    addressDTO.setLabel(address2.getLabel());
                    addressDTO.setCurrBalance("0");
                    addressDTO.setUsdtBalance("0");
                }
                list.add(addressDTO);
            }
            if (!CollectionUtils.isEmpty(list)) {
                this.addressMapper.deleteByAddress(chain, addressList);
                this.addressMapper.saveAll(list);
            }
        }
        res.put("list", list);
        res.put("chain", chain);
        return res;
    }

    private List<Address> getAddressInfoNew(List<String> addressList, String chain) {
        List<Address> list = new ArrayList<>();
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("Ok-Access-Key", "8fabf729-6947-464a-80e5-ca98c552b603");
        DecimalFormat df = new DecimalFormat("#,##0.000000000000");
        for (String addressVal : addressList) {
            if (!StringUtils.isEmpty(addressVal)) {
                String queryUrl;
                Address addressDTO = new Address();
                addressDTO.setAddress(addressVal);
                if ("TRX".equals(chain)) {
                    queryUrl = "https://openapi.misttrack.io/v1/address_labels?coin=USDT-TRC20&address=" + addressVal + "&api_key=gkhicE7LjaQynwdRISZDNWK5M6r2BFG8";
                } else if ("ETH".equals(chain)) {
                    queryUrl = "https://openapi.misttrack.io/v1/address_labels?coin=USDT-ERC20&address=" + addressVal + "&api_key=gkhicE7LjaQynwdRISZDNWK5M6r2BFG8";
                } else {
                    queryUrl = "https://openapi.misttrack.io/v1/address_labels?coin=USDT-BEP20&address=" + addressVal + "&api_key=gkhicE7LjaQynwdRISZDNWK5M6r2BFG8";
                }
                String response = HttpUtil.doGet(queryUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONArray array = data.getJSONArray("label_list");
                    if (array != null && !array.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < array.size(); i++)
                            sb.append(array.getString(i)).append(",");
                        String label = sb.toString().endsWith(",") ? sb.deleteCharAt(sb.length() - 1).toString() : "";
                        addressDTO.setLabel(label);
                    }
                }
                chain = "TRX".equals(chain) ? "TRON" : chain;
                String queryBalanceUrl = "https://www.oklink.com/api/v5/explorer/address/address-summary?chainShortName=" + chain + "&address=" + addressVal;
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONArray data = res.getJSONArray("data");
                    if (data != null && !data.isEmpty()) {
                        String balance = data.getJSONObject(0).getString("balance");
                        addressDTO.setCurrBalance(balance);
                    }
                }
                queryBalanceUrl = "https://www.oklink.com/api/v5/explorer/address/address-balance-fills?chainShortName=" + chain + "&address=" + addressVal + "&protocolType=token_20&tokenContractAddress=";
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONArray data = res.getJSONArray("data");
                    if (data != null && !data.isEmpty()) {
                        JSONArray tokenList = data.getJSONObject(0).getJSONArray("tokenList");
                        if (tokenList != null && !tokenList.isEmpty())
                            for (int i = 0; i < tokenList.size(); i++) {
                                if ("USDT".equals(tokenList.getJSONObject(i).getString("token"))) {
                                    String usdtBalance = tokenList.getJSONObject(i).getString("holdingAmount");
                                    usdtBalance = "BSC".equals(chain) ? ("USDT:" + usdtBalance + " ") : usdtBalance;
                                    addressDTO.setUsdtBalance(usdtBalance);
                                } else if ("BUSD".equals(tokenList.getJSONObject(i).getString("token"))) {
                                    String busdBalance = tokenList.getJSONObject(i).getString("holdingAmount");
                                    busdBalance = "BSC".equals(chain) ? ("BUSD:" + busdBalance + " ") : busdBalance;
                                    addressDTO.setUsdtBalance(busdBalance);
                                }
                            }
                    }
                }
                list.add(addressDTO);
            }
        }
        return list;
    }

    public Object phoneQuery(String phone) {
        List<Map<String, String>> result = new ArrayList<>();
        if (!StringUtils.isEmpty(phone)) {
            String[] phoneStrList = phone.split("\n");
            Set<String> set = new HashSet<>();
            for (String s : phoneStrList) {
                if (!StringUtils.isEmpty(s))
                    set.add(s.substring(0, 7));
            }
            List<Map<String, String>> queryRes = this.phoneMapper.queryByPrefix(new ArrayList<>(set));
            Map<String, Map<String, String>> queryResMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(queryRes))
                for (int i = 0; i < queryRes.size(); i++)
                    queryResMap.put((String)((Map)queryRes.get(i)).get("num_segment"), queryRes.get(i));
            for (String s : phoneStrList) {
                Map<String, String> res = new HashMap<>();
                res.put("phone", s);
                Map<String, String> queryR = queryResMap.get(s.substring(0, 7));
                if (queryR != null) {
                    res.put("province", queryR.get("province"));
                    res.put("city", queryR.get("city"));
                    res.put("server", queryR.get("server"));
                }
                result.add(res);
            }
        }
        return result;
    }

    public Object idcardQuery(String idcard) {
        List<Map<String, String>> result = new ArrayList<>();
        if (!StringUtils.isEmpty(idcard)) {
            String[] idcardStrList = idcard.split("\n");
            Set<String> set = new HashSet<>();
            for (String s : idcardStrList) {
                if (!StringUtils.isEmpty(s))
                    set.add(s.substring(0, 6));
            }
            List<Map<String, String>> queryRes = this.idCardMapper.queryByPrefix(new ArrayList<>(set));
            Map<String, Map<String, String>> queryResMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(queryRes))
                for (int i = 0; i < queryRes.size(); i++)
                    queryResMap.put((String)((Map)queryRes.get(i)).get("id_card_prefix"), queryRes.get(i));
            for (String s : idcardStrList) {
                Map<String, String> res = new HashMap<>();
                res.put("idcard", s);
                Map<String, String> queryR = queryResMap.get(s.substring(0, 6));
                if (queryR != null) {
                    res.put("province", queryR.get("province"));
                    res.put("city", queryR.get("city"));
                    res.put("area", queryR.get("area"));
                }
                result.add(res);
            }
        }
        return result;
    }

    public Object bankcardQuery(String bankcard) {
        List<Map<String, String>> result = new ArrayList<>();
        if (!StringUtils.isEmpty(bankcard)) {
            String[] bankcardStrList = bankcard.split("\n");
            Set<String> set = new HashSet<>();
            for (String s : bankcardStrList) {
                if (!StringUtils.isEmpty(s))
                    set.add(s.substring(0, 6));
            }
            List<Map<String, String>> queryRes = this.bankCardMapper.queryByPrefix(new ArrayList<>(set));
            Map<String, Map<String, String>> queryResMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(queryRes))
                for (int i = 0; i < queryRes.size(); i++)
                    queryResMap.put((String)((Map)queryRes.get(i)).get("bank_card_prefix"), queryRes.get(i));
            for (String s : bankcardStrList) {
                Map<String, String> res = new HashMap<>();
                res.put("bankcard", s);
                Map<String, String> queryR = queryResMap.get(s.substring(0, 6));
                if (queryR != null) {
                    res.put("bank", queryR.get("bank"));
                    res.put("type", queryR.get("type"));
                }
                result.add(res);
            }
        }
        return result;
    }

    public Object listByPage(int page, int pageSize, String chain, String address, String label) {
        PageInfo<Address> result = new PageInfo();
        int count = this.addressMapper.getRecords(chain, address, label);
        int offset = (page - 1) * pageSize;
        List<Address> addressList = this.addressMapper.getWithPage(Integer.valueOf(offset), Integer.valueOf(pageSize), chain, address, label);
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setRecords(count);
        result.setRows(addressList);
        result.setTotal(count);
        return Result.of(result);
    }

    public void deleteDupAddress() {
        this.addressMapper.deleteLabelNull();
        this.addressMapper.deleteDupAddress();
    }

    private List<Address> getAddressInfoBSC(String apiKey, List<String> addressList) {
        List<Address> list = new ArrayList<>();
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("x-apiKey", apiKey);
        DecimalFormat df = new DecimalFormat("#,##0.000000000000");
        for (String addressVal : addressList) {
            if (!StringUtils.isEmpty(addressVal)) {
                Address addressDTO = new Address();
                addressDTO.setAddress(addressVal);
                String queryUrl = "https://www.oklink.com/api/explorer/v1/bsc/address/" + addressVal + "/more";
                String response = HttpUtil.doGet(queryUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONArray array = data.getJSONArray("entityTags");
                    if (array != null && !array.isEmpty()) {
                        String label = array.getString(0);
                        if (label != null && label.contains("Exchange:"))
                            label = label.replace("Exchange:", "");
                        addressDTO.setLabel(label);
                    }
                }
                String queryBalanceUrl = "https://www.oklink.com/api/explorer/v1/bsc/addresses/" + addressVal;
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    Double balance = data.getDouble("balance");
                    if (balance != null) {
                        addressDTO.setCurrBalance(df.format(balance));
                    } else {
                        addressDTO.setCurrBalance("0");
                    }
                }
                queryBalanceUrl = "https://www.oklink.com/api/explorer/v1/bsc/addresses/" + addressVal + "/holders?t=1665235404343&offset=0&limit=20&tokenType=BEP20";
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONArray array = data.getJSONArray("hits");
                    if (array != null && !array.isEmpty()) {
                        String usdtBalanceStr = "";
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if ("USDT".equals(obj.getString("symbol"))) {
                                Double usdtBalance = obj.getDouble("value");
                                if (usdtBalance != null) {
                                    usdtBalanceStr = usdtBalanceStr + "USDT:" + df.format(usdtBalance) + " ";
                                } else {
                                    usdtBalanceStr = usdtBalanceStr + "USDT:0 ";
                                }
                            } else if ("BUSD".equals(obj.getString("symbol"))) {
                                Double usdtBalance = obj.getDouble("value");
                                if (usdtBalance != null) {
                                    usdtBalanceStr = usdtBalanceStr + "BUSD:" + df.format(usdtBalance) + " ";
                                } else {
                                    usdtBalanceStr = usdtBalanceStr + "BUSD:0 ";
                                }
                            }
                        }
                        addressDTO.setUsdtBalance(usdtBalanceStr);
                    }
                }
                list.add(addressDTO);
            }
        }
        return list;
    }

    private List<Address> getAddressInfoETH(String apiKey, List<String> addressList) {
        List<Address> list = new ArrayList<>();
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("x-apiKey", apiKey);
        DecimalFormat df = new DecimalFormat("#,##0.000000000000");
        for (String addressVal : addressList) {
            if (!StringUtils.isEmpty(addressVal)) {
                Address addressDTO = new Address();
                addressDTO.setAddress(addressVal);
                String url = "https://www.oklink.com/api/explorer/v1/eth/address/" + addressVal + "/more";
                String response = HttpUtil.doGet(url, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    if (res != null && res.getJSONObject("data") != null) {
                        JSONObject data = res.getJSONObject("data");
                        JSONArray tagList = data.getJSONArray("entityTags");
                        if (tagList != null && !tagList.isEmpty()) {
                            String label = tagList.getString(0);
                            if (!StringUtils.isEmpty(label)) {
                                label = label.replace("Exchange:", "");
                                addressDTO.setLabel(label);
                            }
                        }
                    }
                }
                response = HttpUtil.doGet("https://www.oklink.com/api/explorer/v1/eth/addresses/" + addressVal, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    if (res != null && res.getJSONObject("data") != null) {
                        JSONObject data = res.getJSONObject("data");
                        Double balance = data.getDouble("balance");
                        if (balance != null) {
                            addressDTO.setCurrBalance(df.format(balance));
                        } else {
                            addressDTO.setCurrBalance("0");
                        }
                    }
                }
                String queryBalanceUrl = "https://www.oklink.com/api/explorer/v1/eth/addresses/" + addressVal + "/holders?offset=0&limit=20&tokenType=ERC20";
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONArray array = data.getJSONArray("hits");
                    if (array != null && !array.isEmpty())
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if ("USDT".equals(obj.getString("symbol"))) {
                                Double usdtBalance = obj.getDouble("value");
                                if (usdtBalance != null) {
                                    addressDTO.setUsdtBalance(df.format(usdtBalance));
                                    break;
                                }
                                addressDTO.setUsdtBalance("0");
                                break;
                            }
                        }
                }
                list.add(addressDTO);
            }
        }
        return list;
    }

    private List<Address> getAddressInfoTRX(String apiKey, List<String> addressList) {
        List<Address> list = new ArrayList<>();
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("x-apiKey", apiKey);
        DecimalFormat df = new DecimalFormat("#,##0.000000000000");
        for (String addressVal : addressList) {
            if (!StringUtils.isEmpty(addressVal)) {
                Address addressDTO = new Address();
                addressDTO.setAddress(addressVal);
                String url = "https://www.oklink.com/api/explorer/v1/tron/address/" + addressVal + "/more";
                String response = HttpUtil.doGet(url, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    if (res != null && res.getJSONObject("data") != null) {
                        JSONObject data = res.getJSONObject("data");
                        JSONArray tagList = data.getJSONArray("entityTags");
                        if (tagList != null && !tagList.isEmpty()) {
                            String label = tagList.getString(0);
                            if (!StringUtils.isEmpty(label)) {
                                label = label.replace("Exchange:", "");
                                addressDTO.setLabel(label);
                            }
                        }
                    }
                }
                response = HttpUtil.doGet("https://www.oklink.com/api/explorer/v1/tron/addresses/info/" + addressVal, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    if (res != null && res.getJSONObject("data") != null) {
                        JSONObject data = res.getJSONObject("data");
                        Double balance = data.getDouble("balance");
                        if (balance != null) {
                            addressDTO.setCurrBalance(df.format(balance));
                        } else {
                            addressDTO.setCurrBalance("0");
                        }
                    }
                }
                String queryBalanceUrl = "https://www.oklink.com/api/explorer/v1/tron/holders/tokens/" + addressVal + "/TRC20?offset=0&limit=10&tokenType=TRC20";
                response = HttpUtil.doGet(queryBalanceUrl, propertyMap);
                if (!StringUtils.isEmpty(response)) {
                    JSONObject res = JSONObject.parseObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONArray array = data.getJSONArray("hits");
                    if (array != null && !array.isEmpty())
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if ("USDT".equals(obj.getString("symbol"))) {
                                Double usdtBalance = obj.getDouble("value");
                                if (usdtBalance != null) {
                                    addressDTO.setUsdtBalance(df.format(usdtBalance));
                                    break;
                                }
                                addressDTO.setUsdtBalance("0");
                                break;
                            }
                        }
                }
                list.add(addressDTO);
            }
        }
        return list;
    }
}
