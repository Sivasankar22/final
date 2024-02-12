package com.example.resume.resumeproject.Transformers;


import com.example.resume.resumeproject.Entities.ThirdParty;

import com.example.resume.resumeproject.RequestDtos.AddThirdPartyRequest;

public class ThirdPartyTransforms {

    public static ThirdParty convertAddThirdPartyReqToAddThirdParty(AddThirdPartyRequest addThirdPartyRequest){

        ThirdParty thirdParty = ThirdParty.builder().organization(addThirdPartyRequest.getOrganization())
                .email(addThirdPartyRequest.getEmail())
                .mobile(addThirdPartyRequest.getMobile())
                .build();
        return thirdParty;
    }
}
