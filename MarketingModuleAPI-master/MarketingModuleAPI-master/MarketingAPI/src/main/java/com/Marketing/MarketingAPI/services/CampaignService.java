package com.Marketing.MarketingAPI.services;
import com.Marketing.MarketingAPI.DTO.CampaignDTO;
import com.Marketing.MarketingAPI.DTO.ClientDTO;
import com.Marketing.MarketingAPI.DTO.ProductDto;
import com.Marketing.MarketingAPI.models.*;
import com.Marketing.MarketingAPI.repositories.CampaignRepo;
import com.Marketing.MarketingAPI.repositories.ClientRepo;
import com.Marketing.MarketingAPI.repositories.ProductRepo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CampaignService {
    public final CampaignRepo campaignRepo;
    public final ClientRepo clientRepo;
    public final ModelMapper modelMapper;
    public final ProductRepo productRepo;
    public final EmailService emailService;
    public List<CampaignDTO> getAllCampaigns(){
        List<Campaign> campaigns =  campaignRepo.findAll();
        return campaigns.stream().map(c -> convertToDTO(c)).collect(Collectors.toList());
    }
    public List<CampaignDTO> getAllDraftCampaigns(){
        List<CampaignDTO> campaigns=new ArrayList<>();
        for(CampaignDTO c:this.getAllCampaigns() ){
            if(c.getStatus().equals(CampaignStatus.Draft)) campaigns.add(c);
        }
        return campaigns;
    }
    public CampaignDTO getCampaignById(Long id){
        Campaign campaign = campaignRepo.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Campaign not found !"));
        return convertToDTO(campaign);
    }
    public CampaignDTO addCampaignDraft(CampaignDTO campaignDTO){
        Campaign campaign = modelMapper.map(campaignDTO,Campaign.class);
        Product product =productRepo.findById(campaignDTO.getProduct_id()).get();
        campaign.setProduct(product);
        campaign.setStatus(CampaignStatus.Draft);
        Campaign savedCampaign = campaignRepo.save(campaign);
        return convertToDTO(savedCampaign);
    }
    public CampaignDTO addClientsToCampaign(Long campaignId,List<ClientDTO> clientsDTO){

        Campaign campaign = campaignRepo.findById(campaignId).get();
        campaign.setClients(clientsDTO.stream().map(c->convertToClient(c)).collect(Collectors.toList()));
        return convertToDTO(campaignRepo.save(campaign));
    }
    public CampaignDTO lauchCampaign(Long campaignId) throws MessagingException {
        Campaign campaign = campaignRepo.findById(campaignId).get();
        campaign.setStatus(CampaignStatus.Launched);
        campaign.setStartDate(LocalDateTime.now());
        List<String> toMails=new ArrayList<>();
        for (Client client:campaign.getClients()) toMails.add(client.getEmail());
        emailService.sendEmailToPeople(toMails,campaignId,campaign.getName(),campaign.getCampaignText());
        return convertToDTO(campaignRepo.save(campaign));
    }
    public CampaignDTO convertToDTO(Campaign campaign){
        CampaignDTO campaignDTO=modelMapper.map(campaign,CampaignDTO.class);
        campaignDTO.setProduct_id(campaign.getProduct().getId());
        return campaignDTO;
    }
    public Client convertToClient(ClientDTO clientDTO){
        return modelMapper.map(clientDTO,Client.class);
    }

}
