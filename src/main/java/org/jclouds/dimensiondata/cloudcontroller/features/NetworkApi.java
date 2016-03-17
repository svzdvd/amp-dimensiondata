/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.dimensiondata.cloudcontroller.features;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks;
import org.jclouds.collect.PagedIterable;
import org.jclouds.dimensiondata.cloudcontroller.domain.NatRule;
import org.jclouds.dimensiondata.cloudcontroller.domain.NetworkDomain;
import org.jclouds.dimensiondata.cloudcontroller.domain.PaginatedCollection;
import org.jclouds.dimensiondata.cloudcontroller.domain.PublicIpBlock;
import org.jclouds.dimensiondata.cloudcontroller.domain.Response;
import org.jclouds.dimensiondata.cloudcontroller.domain.Vlan;
import org.jclouds.dimensiondata.cloudcontroller.parsers.ParseNatRules;
import org.jclouds.dimensiondata.cloudcontroller.parsers.ParseNetworkDomains;
import org.jclouds.dimensiondata.cloudcontroller.options.PaginationOptions;
import org.jclouds.dimensiondata.cloudcontroller.parsers.ParsePublicIpBlocks;
import org.jclouds.dimensiondata.cloudcontroller.parsers.ParseVlans;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.binders.BindToJsonPayload;

@RequestFilters({BasicAuthentication.class})
@Consumes(MediaType.APPLICATION_JSON)
@Path("/network")
public interface NetworkApi {

    @Named("network:list")
    @GET
    @Path("/networkDomain")
    @ResponseParser(ParseNetworkDomains.class)
    @Fallback(Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404.class)
    PaginatedCollection<NetworkDomain> listNetworkDomains(PaginationOptions options);

    @Named("network:list")
    @GET
    @Path("/networkDomain")
    @Transform(ParseNetworkDomains.ToPagedIterable.class)
    @ResponseParser(ParseNetworkDomains.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PagedIterable<NetworkDomain> listNetworkDomains();

    @Named("network:vlan")
    @GET
    @Path("/vlan")
    @ResponseParser(ParseVlans.class)
    @Fallback(Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404.class)
    PaginatedCollection<Vlan> listVlans(@QueryParam("networkDomainId") String networkDomainId, PaginationOptions options);

    @Named("network:vlan")
    @GET
    @Path("/vlan")
    @Transform(ParseVlans.ToPagedIterable.class)
    @ResponseParser(ParseVlans.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PagedIterable<Vlan> listVlans(@QueryParam("networkDomainId") String networkDomainId);

    @Named("network:publicIpBlock")
    @GET
    @Path("/publicIpBlock")
    @ResponseParser(ParsePublicIpBlocks.class)
    @Fallback(Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404.class)
    PaginatedCollection<PublicIpBlock> listPublicIPv4AddressBlocks(@QueryParam("networkDomainId") String networkDomainId, PaginationOptions options);

    @Named("network:publicIpBlock")
    @GET
    @Path("/publicIpBlock")
    @Transform(ParsePublicIpBlocks.ToPagedIterable.class)
    @ResponseParser(ParsePublicIpBlocks.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PagedIterable<PublicIpBlock> listPublicIPv4AddressBlocks(@QueryParam("networkDomainId") String networkDomainId);

    @Named("network:createNatRule")
    @POST
    @Path("/createNatRule")
    @Produces(MediaType.APPLICATION_JSON)
    @MapBinder(BindToJsonPayload.class)
    Response createNatRule(@PayloadParam("networkDomainId") String networkDomainId, @PayloadParam("internalIp") String internalIp,
                          @PayloadParam("externalIp") String externalIp);

    @Named("network:natRule")
    @GET
    @Path("/natRule")
    @ResponseParser(ParseNatRules.class)
    @Fallback(Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404.class)
    PaginatedCollection<NatRule> listNatRules(@QueryParam("networkDomainId") String networkDomainId, PaginationOptions options);

    @Named("network:natRule")
    @GET
    @Path("/natRule")
    @Transform(ParseNatRules.ToPagedIterable.class)
    @ResponseParser(ParseNatRules.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PagedIterable<NatRule> listNatRules(@QueryParam("networkDomainId") String networkDomainId);

    @Named("network:deleteNatRule")
    @POST
    @Path("/deleteNatRule")
    @Produces(MediaType.APPLICATION_JSON)
    @MapBinder(BindToJsonPayload.class)
    Response deleteNatRule(@PayloadParam("id") String natRuleId);
}
