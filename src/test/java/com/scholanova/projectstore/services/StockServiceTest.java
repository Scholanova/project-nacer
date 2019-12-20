package com.scholanova.projectstore.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StockRepository;
import com.scholanova.projectstore.repositories.StoreRepository;

// la couche service permet de s'avoir si une choses existe ou non. Pour savoir si un teste doit etre fait
//dans cette couche ou non il faut se dire si cette action peut etre realiser par
//l'homme exemple ( verifier son stock de legume a l'aide d'un papier) il faut se dire si l'appli devrait
// s'arreter, qu'elle sont les choses qui fonctionnerai quand meme dans l'industrie comme compter son stock

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {
	
	private Integer StoreID = 1;

	private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        stockService = new StockService(stockRepository);
    }
    
    @Test
    void givenExistingStockId_whenCallGetStockById_thenReturnExistingStock() throws ModelNotFoundException, StoreNameCannotBeEmptyException {
    	// GIVEN
        Integer id = 1234;
        String name = "BHV";
        
        Stock existingStock = new Stock(id, StoreID, name, "test", 1);
        when(stockRepository.getById(id)).thenReturn(existingStock);

        // WHEN
        Stock returnedStock = stockService.get(id);

        // THEN
        verify(stockRepository, atLeastOnce()).getById(id);
        assertThat(returnedStock).isEqualTo(existingStock);
    }
    
    @Test
    void givenNotExistingStockId_whenCallGetStockById_thenThrowModelNotFoundException() throws ModelNotFoundException, StoreNameCannotBeEmptyException{
    	// GIVEN
        Integer id = 1234;
        
        when(stockRepository.getById(id)).thenThrow(new ModelNotFoundException());
        
        // WHEN
        // THEN
        assertThrows(ModelNotFoundException.class, () -> {
        	stockService.get(id);
        });
    }
	
    @Test
    void givenExistingStock_WhenTypeIsNail_thenThrowModelNotFoundException() throws ModelNotFoundException, StoreNameCannotBeEmptyException{
    	// GIVEN  givenExistingStock_WhenTypeIsNail_thenThrowModelNotFoundException()
        Integer id = 1234;
        
        String name = null;
        
        Stock existingStock = new Stock(id, StoreID, name, "test", 1);
        
        if(existingStock.getType() == "Fruit" || existingStock.getType() == "Nail") {
            when(stockRepository.getById(id)).thenReturn(existingStock);
            Stock returnedStock = stockService.get(id);
            verify(stockRepository, atLeastOnce()).getById(id);
            assertThat(returnedStock).isEqualTo(existingStock);


        }else {
            when(stockRepository.getById(id)).thenThrow(new ModelNotFoundException());
            
            assertThrows(ModelNotFoundException.class, () -> {
            	stockService.get(id);
            });
        }
        
        
       
        
        // WHEN
       // Stock returnedStock = stockService.get(id);

        // THEN
      //  verify(stockRepository, atLeastOnce()).getById(id);
       // assertThat(returnedStock).isEqualTo(existingStock);
       
    }

}
