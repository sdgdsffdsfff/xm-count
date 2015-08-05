package com.xiaomi.count.bean;

import com.xiaomi.count.model.Agent;
import com.xiaomi.count.service.AgentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理商查找
 * Created by lijie on 2015-07-14.
 */
public class AgentSeeker {

    private AgentService agentService;

    private Map<String, String> map;

    public void init(){
        try {
            map = new HashMap<String, String>();
            List<Agent> agentList = agentService.getListByHQL("from Agent");
            for (Agent agent : agentList) {
                map.put(agent.getValue(), agent.getText());
            }
        } catch (Exception e) {

        }
    }

    public String getAgentText(String value) {
        return map.get(value);
    }

    public AgentService getAgentService() {
        return agentService;
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
    }
}
