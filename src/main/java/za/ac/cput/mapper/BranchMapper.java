package za.ac.cput.mapper;

import za.ac.cput.domain.Branch;
import za.ac.cput.dto.request.BranchRequest;
import za.ac.cput.dto.response.BranchResponse;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toEntity(BranchRequest r) {
        Branch b = new Branch();
        b.setBranchName(r.branchName());
        b.setStreetAddress(r.streetAddress());
        b.setCity(r.city());
        b.setProvince(r.province());
        b.setPostalCode(r.postalCode());
        b.setPhoneNumber(r.phoneNumber());
        b.setEmail(r.email());
        return b;
    }

    public BranchResponse toResponse(Branch b) {
        if (b == null) {
            return null;
        }
        return new BranchResponse(
                b.getBranchId(), b.getBranchName(), b.getStreetAddress(), b.getCity(),
                b.getProvince(), b.getPostalCode(), b.getPhoneNumber(), b.getEmail());
    }
}
