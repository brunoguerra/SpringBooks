/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apress.prospringintegration.messageflow.workflow;

import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public class ActivitiProcessEngineConfiguration {

    private Log log = LogFactory.getLog(getClass());

    @Value("#{dataSource}")
    private DataSource dataSource;

    @PostConstruct
    public void setup() {
        log.debug("starting up " + getClass().getName());
    }

    private String getDatabaseSchemaUpdate() {
        return SpringProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean =
                new ProcessEngineFactoryBean();

        SpringProcessEngineConfiguration configuration =
                new SpringProcessEngineConfiguration();
        configuration.setTransactionManager(dataSourceTransactionManager());
        configuration.setDatabaseType("h2");
        configuration.setJobExecutorActivate(false);
        configuration.setDataSource(targetDataSource());
        configuration.setDatabaseSchemaUpdate(getDatabaseSchemaUpdate());
        processEngineFactoryBean.setProcessEngineConfiguration(configuration);
        return processEngineFactoryBean;
    }

    @Bean
    public DataSource targetDataSource() {
        TransactionAwareDataSourceProxy transactionAwareDataSourceProxy =
                new TransactionAwareDataSourceProxy();
        transactionAwareDataSourceProxy.setTargetDataSource(dataSource);
        return transactionAwareDataSourceProxy;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager =
                new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(this.targetDataSource());
        return dataSourceTransactionManager;
    }
}
