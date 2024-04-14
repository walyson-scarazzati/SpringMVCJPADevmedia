package br.com.devmedia.curso.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.devmedia.curso.dao.UsuarioDao;
import br.com.devmedia.curso.domain.TipoSexo;
import br.com.devmedia.curso.domain.Usuario;

@Controller
// define um caminho (path), quando utiliza apenas o atributo value dentro da notação RequestMapping não precisa adicionar o atributo value
// basta adicionar a string por padrão vai entender que esse valor seria respectivo ao value
// para acessar esse controller depois da / principal da aplicação nome usuario ou string usuario 
@RequestMapping("usuario")
public class UsuarioController {
	
	// injetar dentro do controller 
	@Autowired
    private UsuarioDao dao;
	
	// deixa lista de sexo disponivel @ModelAttribute somente chamando "sexo" no front 
	@ModelAttribute("sexos")
    public TipoSexo[] tipoSexo() {
        return TipoSexo.values();
    }
     
	// consulta nome
    @GetMapping("/nome")
    public ModelAndView listarPorNome(@RequestParam(value = "nome") String nome) {
 
        if (nome == null) {
            return new ModelAndView("redirect:/usuario/todos");
        }
        return new ModelAndView("/user/list", "usuarios", dao.getByNome(nome));
    }   
     
 // consulta tipo sexo
    @GetMapping("/sexo")
    public ModelAndView listarPorSexo(@RequestParam(value = "tipoSexo") TipoSexo sexo) {
    	// se for nulo ele retorna para todos
        if (sexo == null) {
            return new ModelAndView("redirect:/usuario/todos");
        }
        return new ModelAndView("/user/list", "usuarios", dao.getBySexo(sexo));
    }
    
	// devolve a lista de usuarios
    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public ModelAndView listaTodos(ModelMap model) {
    	// para adicionar o quer enviar para a página
    	// variavel usuarios  vai armazenar a lista 
        model.addAttribute("usuarios", dao.getTodos());
        // coloca no parâmetro  o destino que é a página que eu quero enviar
        // é necessário colocar o diretorio "user" pois a página encontra-se lá
        // depois coloca objeto que quer enviar
        return new ModelAndView("/user/list", model);
    }
     
    @GetMapping("/cadastro")
    // por que usar o ModelAttribute porque a página add.jsp tem um formulario e nesse formulário tem um model attribute "usuario"
    // o  tipo de objeto que vai trabalhar no formulario? Um objeto do tipo "usuario", define aqui objeto do tipo "usuario" 
    public String cadastro(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
    	//tira a linha seguinte pois criou um metodo de deixa a lista de sexo exposto
       // model.addAttribute("sexos", TipoSexo.values());
        return "/user/add";
    }
     
    @PostMapping("/save") // ou pode usar @RequestMapping(value = "/save", method = RequestMethod.POST) que é a mesma coisa
    // quando faz um redirect não pode enviar mensagem via ModelMap! Porque o ModelMapping funciona apenas no sistema forward 
    // que é quando você não faz um redirect, se faz o redirect usando o modelMap fazendo uma nova solicitação os dados do Model se perdem
    // usa o RedirectAttributes que tem o metodo addFlashAttribute que proporciona enviar para página no redirect qualquer tipo de variavél 
    // @Valid vai vlaidar objeto usuário que está chegando no controller
    public String save(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, RedirectAttributes attr) {
      // além do da validação pode adicionar uma espécie de alteração de fluxo da solicação quando tem um problema de validação
     // exemplo o campo não venho preenchido quer retornar para o formulário com a mensagem da validação
    // BindingResult esse objeto deve esta obrigatoriamente antes do  RedirectAttributes caso coloque depois o Spring não vai reconhecer
    	
    	if (result.hasErrors()) {
            return "/user/add";
        }
        dao.salvar(usuario);
        attr.addFlashAttribute("message", "Usuário salvo com sucesso.");
        return "redirect:/usuario/todos";
    }
     
    // @PathVariable essa anotação captura o valor que a gente está recebendo na URL adiciona ela ao um objeto
    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") Long id, ModelMap model) {
        Usuario usuario = dao.getId(id);
        model.addAttribute("usuario", usuario);
        return new ModelAndView("/user/add", model);
    }
     
    // usa @ModelAttribute por que está recebendo do fomulário um objeto usuario dessa vez
    // RedirectAttributes enviar a mensagem para front
    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return new ModelAndView("/user/add");
        }
        dao.editar(usuario);
        attr.addFlashAttribute("message", "Usuário alterado com sucesso.");
        return new ModelAndView("redirect:/usuario/todos");
    }
     
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attr) {
        dao.excluir(id);
        attr.addFlashAttribute("message", "Usuário excluído com sucesso.");
        return "redirect:/usuario/todos";
    }

}
