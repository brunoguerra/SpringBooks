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

package com.apress.prospringintegration.gateways.client;

import com.apress.prospringintegration.gateways.model.Ticket;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainJMSGateway {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext( "gateway-jms-service.xml");

        ClassPathXmlApplicationContext ctx1 =
                new ClassPathXmlApplicationContext("gateway-jms-client.xml");

        TicketIssuer ticketIssuer = ctx1.getBean("ticketIssueGateway",
                TicketIssuer.class);

        Ticket ticket = ticketIssuer.issueTicket(1);

        System.out.println("Ticket " + ticket + " was issued on:" +
                ticket.getIssueDateTime() + " with ticket id: " +
                ticket.getTicketId());
    }
}
