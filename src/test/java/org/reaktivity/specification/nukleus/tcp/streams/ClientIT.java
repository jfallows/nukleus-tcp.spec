/**
 * Copyright 2016-2017 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.nukleus.tcp.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;
import org.reaktivity.specification.nukleus.NukleusRule;

public class ClientIT
{
    private final K3poRule k3po = new K3poRule()
            .setScriptRoot("org/reaktivity/specification/nukleus/tcp/streams");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    private final NukleusRule nukleus = new NukleusRule()
        .directory("target/nukleus-itests")
        .streams("tcp", "source")
        .streams("reply", "tcp#any");

    @Rule
    public final TestRule chain = outerRule(nukleus).around(k3po).around(timeout);

    @Test
    @Specification({
        "connection.established/client/nukleus",
        "connection.established/client/source",
        "connection.established/client/reply"
    })
    public void shouldEstablishConnection() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Test
    @Specification({
        "server.sent.data/client/nukleus",
        "server.sent.data/client/source",
        "server.sent.data/client/reply" })
    public void shouldReceiveServerSentData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Test
    @Specification({
        "client.sent.data/client/nukleus",
        "client.sent.data/client/source",
        "client.sent.data/client/reply" })
    public void shouldReceiveClientSentData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Test
    @Specification({
        "echo.data/client/nukleus",
        "echo.data/client/source",
        "echo.data/client/reply" })
    public void shouldEchoData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Test
    @Specification({
        "server.close/client/nukleus",
        "server.close/client/source",
        "server.close/client/reply" })
    public void shouldInitiateServerClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Test
    @Specification({
        "client.close/client/nukleus",
        "client.close/client/source",
        "client.close/client/reply" })
    public void shouldInitiateClientClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }

    @Ignore
    @Test
    @Specification({
        "concurrent.connections/client/nukleus",
        "concurrent.connections/client/source",
        "concurrent.connections/client/reply" })
    public void shouldEstablishConcurrentConnections() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_INITIAL");
        k3po.notifyBarrier("ROUTED_REPLY");
        k3po.finish();
    }
}
