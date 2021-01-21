package pl.testing.tasks2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    CargoRepository cargoRepository;

    @Mock
    UnitRepository unitRepository;

    @InjectMocks
    UnitService cut;

    @DisplayName("- should find cargo by name and return expected values")
    @Test
    void tes1(){
        Cargo cargo = new Cargo("Cargo",20);

        when(cargoRepository.findCargoByName("Cargo")).thenReturn(java.util.Optional.of(cargo));

        Optional<Cargo> result =cargoRepository.findCargoByName("Cargo");

        assertThat(result.get().getWeight()).isEqualTo(20);

    }

    @DisplayName("- should load added cargo")
    @Test
    void test2(){
        Cargo cargo = new Cargo("Cargo",20);
        Unit unit = new Unit(new Coordinates(0,0),10,30);

        when(cargoRepository.findCargoByName("Cargo")).thenReturn(java.util.Optional.of(cargo));

        cut.addCargoByName(unit,"Cargo");

        verify(cargoRepository).findCargoByName("Cargo");
        assertThat(unit.getLoad()).isEqualTo(20);
        assertThat(unit.getCargo().get(0)).isEqualTo(cargo);

    }

    @DisplayName("- should throw exception when no cargo is found")
    @Test
    void test3() {
        Unit unit = new Unit(new Coordinates(0,0),10,30);

        when(cargoRepository.findCargoByName("name")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cut.addCargoByName(unit,"name"));
        assertThatThrownBy(() ->cut.addCargoByName(unit,"name"))
                .hasMessageContaining("Unable to find cargo")
                .isInstanceOf(NoSuchElementException.class)
                .hasNoCause();
    }

    @DisplayName("- should return unit with given coordinates")
    @Test
    void test4() {
        Coordinates coordinates = new Coordinates(10,10);
        Unit unit = new Unit(coordinates,10,10);
        when(unitRepository.getUnitByCoordinates(new Coordinates(10,10))).thenReturn(unit);

        Unit expected = cut.getUnitOn(new Coordinates(10,10));

        assertThat(expected).isSameAs(unit);

    }
    @Test
    @DisplayName("- should raise exception when there is no unit with given coordniates")
    void test5() {
        when(unitRepository.getUnitByCoordinates(new Coordinates(10,20))).thenReturn(null);

        assertThatThrownBy(()-> cut.getUnitOn(new Coordinates(10,20)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Unable to find any unit")
                .hasNoCause();

    }
}