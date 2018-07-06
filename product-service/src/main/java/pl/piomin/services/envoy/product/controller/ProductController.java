package pl.piomin.services.envoy.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpGet;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import pl.piomin.services.envoy.product.model.Product;
import pl.piomin.services.envoy.product.utils.HttpClientUtils;

@RestController
@RequestMapping("/product")
public class ProductController {

	private List<Product> products = new ArrayList<>();
	
	
	public ProductController() {
		Product person = new Product();
		person.setId(1L);
		person.setName("mm");
		products.add(person);
	}

	@GetMapping
	public List<Product> findAll() {
		return products;
	}
	static Set<String> headerKeys = new HashSet<>();
	static 
	{
		String[] TRACE_HEADERS_TO_PROPAGATE = {
		                              "x-ot-span-context",
		                              "x-request-id",
		                              "x-b3-traceid",
		                              "x-b3-spanid",
		                              "x-b3-parentspanid",
		                              "x-b3-sampled",
		                              "x-b3-flags",
		                              "uber-trace-id"
		};
		
		headerKeys.addAll(Arrays.asList(TRACE_HEADERS_TO_PROPAGATE));
		
	}

	
	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	//@GetMapping("/{id}")
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Product findById(@RequestParam("id") Long id,HttpServletRequest request) {
		  System.out.println("------- findById -------id=="+id);
		
		  //调用本地容器中的envoy 代理。去访问 person  容器中 方法。才能把链路穿起来
		  HttpGet get =new HttpGet("http://127.0.0.1:10003/person/findById?id="+id);
			
		  Enumeration<String> requestHeader = request.getHeaderNames();
	        System.out.println("------- header -------");
        while(requestHeader.hasMoreElements()){
            String headerKey=requestHeader.nextElement().toString();
            //打印所有Header值
            if(headerKeys.contains(headerKey.toLowerCase())) {
            	  get.addHeader(headerKey, request.getHeader(headerKey));
            	  System.out.println("headerKey="+headerKey+";value="+request.getHeader(headerKey));
            }
        }
        
    	try {
			System.out.println("id==================="+id);
			JSONObject rs = HttpClientUtils.httpGet(get);;
			System.out.println("id==================="+rs);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return products.stream().filter(it -> it.getId().equals(id)).findFirst().get();
	}

	//@PostMapping
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Product add(@RequestBody Product p) {
		p.setId((long) (products.size() + 1));
		products.add(p);
		return p;
	}

	@DeleteMapping("/{id}")
	public void delete(@RequestParam("id") Long id) {
		List<Product> p = products.stream().filter(it -> it.getId().equals(id)).collect(Collectors.toList());
		products.removeAll(p);
	}

	@PutMapping
	public void update(@RequestBody Product p) {
		Product product = products.stream().filter(it -> it.getId().equals(p.getId())).findFirst().get();
		products.set(products.indexOf(product), p);
	}

}
