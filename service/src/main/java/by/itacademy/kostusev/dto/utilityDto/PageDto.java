package by.itacademy.kostusev.dto.utilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PageDto {

    private Integer page;
    private Integer size;
}
