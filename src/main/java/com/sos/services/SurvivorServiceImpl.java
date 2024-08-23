package com.sos.services;

import com.sos.entities.Survivor;
import com.sos.entities.SurvivorLocation;
import com.sos.exceptions.BaseException;
import com.sos.models.enums.Gender;
import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.requests.ReportContaminateRequest;
import com.sos.models.requests.UpdateSurvivorLocationRequest;
import com.sos.models.responses.ReportContaminationResponse;
import com.sos.models.responses.SurvivorLocationUpdateResponse;
import com.sos.models.responses.SurvivorResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import com.sos.repositories.SurvivorLocationRepository;
import com.sos.repositories.SurvivorRepository;
import com.sos.utils.DateTimeUtil;
import com.sos.validators.CreateSurvivorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.sos.models.InventoryDetail.mapInventoryEntityToInventoryDetails;

@Service
public class SurvivorServiceImpl implements SurvivorService {
    private static final int MAX_COUNT = 3;
    private final Validator createSurvivorValidator;
    private final SurvivorRepository survivorRepository;
    private final SurvivorLocationRepository survivorLocationRepository;

    @Autowired
    public SurvivorServiceImpl(SurvivorRepository survivorRepository, SurvivorLocationRepository survivorLocationRepository) {
       this.createSurvivorValidator = new CreateSurvivorValidator();
       this.survivorRepository = survivorRepository;
       this.survivorLocationRepository = survivorLocationRepository;
   }

    @Transactional
    @Override
    public ApiResponse create(final CreateSurvivorRequest createSurvivorRequest) {
        Optional<Survivor> survivor = survivorRepository.findByNameAndAgeAndIdentity(
                createSurvivorRequest.getName(),
                createSurvivorRequest.getAge(),
                createSurvivorRequest.getIdentity());

        //Duplicate check
        createSurvivorValidator.validate(survivor, null);

        Survivor tempSurvivor = new Survivor(
                createSurvivorRequest.getName(),
                createSurvivorRequest.getAge(),
                createSurvivorRequest.getGender(),
                createSurvivorRequest.getIdentity(),
                createSurvivorRequest.getLocation().getLatitude(),
                createSurvivorRequest.getLocation().getLongitude());

        tempSurvivor.addInventory(createSurvivorRequest.getSupplies());

        Survivor newSurvivor = this.survivorRepository.save(tempSurvivor);

        return new ApiSuccessfulResponse(SurvivorResponse
                .builder()
                .id(newSurvivor.getId())
                .name(newSurvivor.getName())
                .gender(newSurvivor.getGender().getDescription())
                .age(newSurvivor.getAge())
                .identity(newSurvivor.getIdentity())
                .isInfected(newSurvivor.isInfected())
                .updatedDate(DateTimeUtil.formatLongTimestampToDate(newSurvivor.getUpdatedDate()))
                .createdDate(DateTimeUtil.formatLongTimestampToDate(newSurvivor.getCreatedDate()))
                .inventoryDetails(mapInventoryEntityToInventoryDetails(newSurvivor.getAllInventory()))
                .build(),
                "Successfully added survivor.");
    }

    @Transactional
    @Override
    public ApiResponse updateLocation(final Long id, final UpdateSurvivorLocationRequest updateSurvivorLocationRequest) {
        Optional<Survivor> survivor = this.survivorRepository.findById(id);
        if (survivor.isPresent()){
            if (survivor.get().getLastLocation() == null){
                throw new BaseException(HttpStatus.ACCEPTED, "Rogue survivor, no location found for survivor id." + id);
            }
            SurvivorLocation newSurvivorLocation = this.survivorLocationRepository.save(SurvivorLocation
                    .builder()
                    .id(survivor.get().getId())
                    .latitude(updateSurvivorLocationRequest.getLocation().getLatitude())
                    .longitude(updateSurvivorLocationRequest.getLocation().getLongitude())
                    .build());

            return new ApiSuccessfulResponse(SurvivorLocationUpdateResponse
                    .builder()
                    .id(newSurvivorLocation.getId())
                    .latitude(newSurvivorLocation.getLatitude())
                    .longitude(newSurvivorLocation.getLongitude())
                    .build(),
                    "Successfully updated survivor location.");
        }
        throw new BaseException(HttpStatus.NOT_FOUND, "Survivor not found for survivorId=" + id);
    }

    @Transactional
    @Override
    public ApiResponse reportContaminateCase(ReportContaminateRequest reportContaminateRequest) {
        Optional<Survivor> accusedSurvivor = this.survivorRepository.findByNameAndGenderAndIdentity(
                reportContaminateRequest.getAccused().getName(),
                Gender.getGender(reportContaminateRequest.getAccused().getGender()),
                reportContaminateRequest.getAccused().getIdentity());

        if(accusedSurvivor.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND, String.format("Survivor with name %s not found." , reportContaminateRequest.getAccused().getName()));
        }


        Survivor survivor = accusedSurvivor.get();
        reportContaminateRequest.getReporterIds().forEach(survivor::addContaminationReporter);
        if (isMaxCounterReached(survivor.getAccusedCount(), reportContaminateRequest.totalReporters())) {
            survivor.setInfected(true);
            this.survivorRepository.save(survivor);

            return new ApiSuccessfulResponse(ReportContaminationResponse
                    .builder()
                    .id(survivor.getId())
                    .name(survivor.getName())
                    .age(survivor.getAge())
                    .gender(survivor.getGender().getDescription())
                    .identity(survivor.getIdentity())
                    .isInfected(survivor.isInfected())
                    .accusedCount((byte) survivor.getContaminationReport().size())
                    .build(),
                    "Survivor infected please evacuate immediately!");
        }

        this.survivorRepository.save(survivor);

        return new ApiSuccessfulResponse(ReportContaminationResponse
                .builder()
                .id(survivor.getId())
                .name(survivor.getName())
                .age(survivor.getAge())
                .gender(survivor.getGender().getDescription())
                .identity(survivor.getIdentity())
                .isInfected(survivor.isInfected())
                .accusedCount((byte) survivor.getContaminationReport().size())
                .build(),
                "Successfully submitted the contamination requested.");
    }

    private static boolean isMaxCounterReached(int numberOfAccusations, int numberOfReporters) {
        return numberOfAccusations == MAX_COUNT || numberOfReporters == MAX_COUNT;
    }
}
