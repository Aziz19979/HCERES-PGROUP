package org.centrale.hceres.dto.stat.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatSummaryDto implements Serializable {
    private List<? extends StatItemDto> items;
}
