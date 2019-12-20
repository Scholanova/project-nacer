package com.scholanova.projectstore.services;

import org.springframework.stereotype.Service;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StockNameCannotBeEmptyException;
import com.scholanova.projectstore.exceptions.StockValueInvalidException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StockRepository;
import com.scholanova.projectstore.repositories.StoreRepository;


@Service
public class StockService {
	
    private StockRepository stockRepository;
    
    public StockRepository getStockRepository() {
		return stockRepository;
	}
    
    public Stock get(Integer id) throws ModelNotFoundException {
    	return stockRepository.getById(id);
    }

	public void setStockRepository(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

	public Stock create(Integer idStore, Stock stock) throws StockNameCannotBeEmptyException, StockValueInvalidException{
		
		if(stock.getType() == "Fruit" || stock.getType() == "Nail" && stock.getName() != null && stock.getValue() >0  ) {
			return stockRepository.create(stock);
		}else {
			return null;
		}
		
	}


	
}
