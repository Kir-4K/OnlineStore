package by.itacademy.kostusev.dto.utilityDto;

import by.itacademy.kostusev.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOrderStatusDto {

    private Long onlineOrderId;
    private Status newStatus;
}
